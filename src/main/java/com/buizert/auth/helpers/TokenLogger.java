package com.buizert.auth.helpers;

import java.text.ParseException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenLogger {

	private static Logger log = LoggerFactory.getLogger(TokenLogger.class);

	public static void log(String token) {
		
        if (Objects.nonNull(token)) {
        	log.info(token.substring(0, 10) + "...");
        	log.info(token);
        }
		SignedJWT jwt;
		try {
			
			jwt = SignedJWT.parse(token);
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			log.info("token content : " + jwt.getPayload().toString());
			log.info("issuer        : " + claims.getIssuer());
			log.info("subject       : " + claims.getSubject());
			log.info("audience      : " + claims.getAudience());
			log.info("roles         : " + claims.getClaim("roles"));
			log.info("scopes        : " + claims.getStringListClaim("scp"));
			
		
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
}
