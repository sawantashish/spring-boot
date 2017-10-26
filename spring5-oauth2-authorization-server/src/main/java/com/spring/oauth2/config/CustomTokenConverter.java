package com.spring.oauth2.config;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

public class CustomTokenConverter extends JwtAccessTokenConverter {

    private JsonParser objectMapper = JsonParserFactory.create();

    private String verifierKey = new RandomValueStringGenerator().generate();

    private Signer signer = new MacSigner(verifierKey);

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        String content;
        try {
            Map<String, Object> response = (Map<String, Object>) getAccessTokenConverter().convertAccessToken(accessToken, authentication);
            response.put("userId", "12345");
            content = objectMapper.formatMap(response);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }
        String token = JwtHelper.encode(content, signer).getEncoded();
        return token;
    }
}
