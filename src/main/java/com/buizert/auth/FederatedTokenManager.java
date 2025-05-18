package com.buizert.auth;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buizert.auth.helpers.AppConfig;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.GrantType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;

@Component
public class FederatedTokenManager {

	@Autowired
	private AppConfig appConfig;
	
	private static Logger log = LoggerFactory.getLogger(FederatedTokenManager.class);
	
	public String getBearerToken(String accessToken) {
	
		log.info("Start authentication for client credential flow");
		
		try {
			
	        // Prepare parameters for token exchange
	        Map<String, List<String>> params = new HashMap<>();
	        params.put("grant_type", Collections.singletonList("client_credentials"));
	        params.put("client_assertion_type", Collections.singletonList("urn:ietf:params:oauth:client-assertion-type:jwt-bearer"));
	        params.put("client_assertion", Collections.singletonList(accessToken));
	        params.put("requested_token_use", Collections.singletonList("on_behalf_of"));
	        
	        AuthorizationGrant grant = new AuthorizationGrant(GrantType.TOKEN_EXCHANGE) {
                @Override
                public Map<String, List<String>> toParameters() {
                    return params;
                }
            };
            
	        // Build token request
	        TokenRequest.Builder builder = new TokenRequest.Builder(
	        		  new URI(appConfig.getFederatedUrl())
	        		, new ClientID(appConfig.getFederatedClientId())
	        		, grant);
	        
	        builder.scope(new Scope(appConfig.getFederatedScope()));
	        TokenRequest request = builder.build();
	        
			final HTTPRequest httpRequest = request.toHTTPRequest();
        
			// Receive the token request into a response
			final TokenResponse response = TokenResponse.parse(httpRequest.send());

			if (response.indicatesSuccess()) {

				final AccessTokenResponse successResponse = response.toSuccessResponse();

				// Get the access token
				final AccessToken federatedAccessToken = successResponse.getTokens().getAccessToken();
				return federatedAccessToken.getValue();
			
			} else {
                       
				final TokenErrorResponse errorResponse = response.toErrorResponse();
				final ErrorObject error = errorResponse.getErrorObject();
				
				log.error("Error during token generation :: " 
        			+ error.getCode() 
        			+ " :: description :: " 
        			+ error.getDescription() 
        			+ " :: http status code :: " 
        			+ error.getHTTPStatusCode());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}
