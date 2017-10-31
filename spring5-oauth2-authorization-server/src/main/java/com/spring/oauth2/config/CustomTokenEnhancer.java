package com.spring.oauth2.config;

import com.spring.oauth2.entity.CustomUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("username", user.getUsername());
        additionalInfo.put("userId", user.getUserId());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}

