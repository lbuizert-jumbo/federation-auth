package com.buizert.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.buizert.auth.helpers.TokenLogger;

/**
 * Generate application:
 * mvn clean verify
 * 
 * Run application:
 * mvn spring-boot:run
 * 
 * @author Leen Buizert
 *
 */

@SpringBootApplication
public class FederatedAuthApp implements CommandLineRunner {
	
	@Autowired
	private LocalTokenManager tokenManager;
	
	@Autowired
	private FederatedTokenManager federatedTokenManager;
	
	private static Logger log = LoggerFactory.getLogger(FederatedAuthApp.class);
	
	public static void main(String[] args) {
		SpringApplication.run(FederatedAuthApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		final String localToken = tokenManager.getFederationToken();
		TokenLogger.log(localToken);
		log.info("Use this token to request token from external IDP");
		final String federatedToken = federatedTokenManager.getBearerToken(localToken);
		TokenLogger.log(federatedToken);
	}
}