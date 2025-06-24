package com.buizert.auth.helpers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AppConfig reads properties from the properties file(s).
 * 
 * @author Leen Buizert
 *
 */
@Component
@ConfigurationProperties(prefix = "config.auth")
public class AppConfig {

	private String localUrl;
	private String localClientId;
	private String localSecret;
	private String localScope;
	private String jumboUrl;
	private String jumboClientId;
	private String jumboSecret;
	private String jumboScope;
	private String federatedUrl;
	private String federatedClientId;
	private String federatedScope;
	
	public String getFederatedScope() {
		return federatedScope;
	}
	public void setFederatedScope(String federatedScope) {
		this.federatedScope = federatedScope;
	}
	public String getFederatedClientId() {
		return federatedClientId;
	}
	public void setFederatedClientId(String federatedClientId) {
		this.federatedClientId = federatedClientId;
	}
	public String getFederatedUrl() {
		return federatedUrl;
	}
	public void setFederatedUrl(String federatedUrl) {
		this.federatedUrl = federatedUrl;
	}
	public String getLocalScope() {
		return localScope;
	}
	public void setLocalScope(String localScope) {
		this.localScope = localScope;
	}
	public String getLocalClientId() {
		return localClientId;
	}
	public void setLocalClientId(String localClientId) {
		this.localClientId = localClientId;
	}
	public String getLocalSecret() {
		return localSecret;
	}
	public void setLocalSecret(String localSecret) {
		this.localSecret = localSecret;
	}
	public String getLocalUrl() {
		return localUrl;
	}
	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
    public String getJumboUrl() {
        return jumboUrl;
    }
    public void setJumboUrl(String jumboUrl) {
        this.jumboUrl = jumboUrl;
    }
    public String getJumboClientId() {
        return jumboClientId;
    }
    public void setJumboClientId(String jumboClientId) {
        this.jumboClientId = jumboClientId;
    }
    public String getJumboSecret() {
        return jumboSecret;
    }
    public void setJumboSecret(String jumboSecret) {
        this.jumboSecret = jumboSecret;
    }
    public String getJumboScope() {
        return jumboScope;
    }
    public void setJumboScope(String jumboScope) {
        this.jumboScope = jumboScope;
    }
}
