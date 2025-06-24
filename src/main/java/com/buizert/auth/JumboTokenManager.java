package com.buizert.auth;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buizert.auth.helpers.AppConfig;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ClientCredentialsGrant;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;

@Component
public class JumboTokenManager {

	@Autowired
	private AppConfig appConfig;
	
	private static Logger log = LoggerFactory.getLogger(JumboTokenManager.class);
	
	public String getFederationToken() {
	
		log.info("Start authentication for client credential flow with Jumbo OKTA app registration");
		
		try {
			// Construct the client credentials grant
			AuthorizationGrant clientGrant = new ClientCredentialsGrant();
			
			ClientID clientID = new ClientID(appConfig.getJumboClientId());
			
			Secret clientSecret = new Secret(appConfig.getJumboSecret());
			ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);
       
			// The request scope for the token (may be optional)
			final Scope clientScope = new Scope(appConfig.getJumboScope());
			final URI endpoint = new URI(appConfig.getJumboUrl());

			// Build the token request
			TokenRequest.Builder builder = new TokenRequest.Builder(endpoint, clientAuth, clientGrant);
			builder.scope(clientScope);
			final TokenRequest request = builder.build();
			final HTTPRequest httpRequest = request.toHTTPRequest();
        
			// Receive the token request into a response
			final TokenResponse response = TokenResponse.parse(httpRequest.send());

			if (response.indicatesSuccess()) {

				final AccessTokenResponse successResponse = response.toSuccessResponse();

				// Get the access token
				final AccessToken accessToken = successResponse.getTokens().getAccessToken();
				return accessToken.getValue();
			
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
