package com.spring.oauth2.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;

import java.util.Map;

public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    private static final Log logger = LogFactory.getLog(JwtAccessTokenConverter.class);

    private JsonParser objectMapper = JsonParserFactory.create();

    private String verifierKey = new RandomValueStringGenerator().generate();

    private Signer signer = new MacSigner(verifierKey);

    private String signingKey = verifierKey;

    private SignatureVerifier verifier;

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        String content;
        try {
            Map<String, Object> response = (Map<String, Object>) getAccessTokenConverter().convertAccessToken(accessToken, authentication);
            //response.put("userId", "12345");
            content = objectMapper.formatMap(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }
        String token = JwtHelper.encode(content, signer).getEncoded();
        return token;
    }

    @Override
    protected Map<String, Object> decode(String token) {
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
            String claimsStr = jwt.getClaims();
            Map<String, Object> claims = objectMapper.parseMap(claimsStr);
            if (claims.containsKey(EXP) && claims.get(EXP) instanceof Integer) {
                Integer intValue = (Integer) claims.get(EXP);
                claims.put(EXP, new Long(intValue));
            }
            this.getJwtClaimsSetVerifier().verify(claims);
            return claims;
        }
        catch (Exception e) {
            throw new InvalidTokenException("Cannot convert access token to JSON", e);
        }
    }



    public void afterPropertiesSet() throws Exception {
        if (verifier != null) {
            // Assume signer also set independently if needed
            return;
        }
        SignatureVerifier verifier = new MacSigner(verifierKey);
        try {
            verifier = new RsaVerifier(verifierKey);
        }
        catch (Exception e) {
            logger.warn("Unable to create an RSA verifier from verifierKey (ignoreable if using MAC)");
        }
        // Check the signing and verification keys match
        if (signer instanceof RsaSigner) {
            byte[] test = "test".getBytes();
            try {
                verifier.verify(test, signer.sign(test));
                logger.info("Signing and verification RSA keys match");
            }
            catch (InvalidSignatureException e) {
                logger.error("Signing and verification RSA keys do not match");
            }
        }
        else if (verifier instanceof MacSigner) {
            // Avoid a race condition where setters are called in the wrong order. Use of
            // == is intentional.
            Assert.state(this.signingKey == this.verifierKey,
                    "For MAC signing you do not need to specify the verifier key separately, and if you do it must match the signing key");
        }
        this.verifier = verifier;
    }
}
