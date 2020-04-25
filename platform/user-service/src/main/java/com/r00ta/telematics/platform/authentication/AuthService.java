package com.r00ta.telematics.platform.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.authentication.models.JwtSchema;
import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuthService implements IAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final PrivateKey pk = readPrivateKey("/privateKey.pem");

    @Inject
    IUserService userService;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public String generateToken(String userId) {

        JwtSchema schema = JwtSchema.createUserJwtSchema(userId);
        HashMap<String, Long> timeClaims = new HashMap<>();
        long exp = currentTimeInSecs() + 60 * 60 * 24 * 30;
        timeClaims.put(Claims.exp.name(), exp);
        return generateTokenString(schema, timeClaims);
    }

    public static String generateTokenString(JwtSchema userJwt, Map<String, Long> timeClaims) {
        // Use the test private key associated with the test public key for a valid signature
        PrivateKey pk = readPrivateKey("/privateKey.pem");
        return generateTokenString(pk, "/privateKey.pem", userJwt, timeClaims);
    }

    public static String generateTokenString(PrivateKey privateKey, String kid,
                                             JwtSchema userJwt, Map<String, Long> timeClaims) {

        JwtClaimsBuilder claims = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();
        long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name())
                ? timeClaims.get(Claims.exp.name()) : currentTimeInSecs + (60 * 60 * 24 * 30); // 1 month validity

        claims.groups(userJwt.groups);
        claims.subject(userJwt.userId);
        claims.issuer("https://quarkus.io/using-jwt-rbac");
        claims.issuedAt(currentTimeInSecs);
        claims.claim(Claims.auth_time.name(), currentTimeInSecs);
        claims.expiresAt(exp);

        return claims.jws().signatureKeyId(kid).sign(privateKey);
    }

    /**
     * Read a PEM encoded private key from the classpath
     *
     * @param pemResName - key file resource name
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    private static PrivateKey readPrivateKey(final String pemResName) {
        try (InputStream contentIS = AuthService.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
        } catch (IOException e) {
            LOGGER.error("Critical: Can not read private key to sign tokens.", e);
            return null;
        }
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    private static PrivateKey decodePrivateKey(final String pemEncoded) {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Critical: RSA key factory not available.", e);
        } catch (InvalidKeySpecException e) {
            LOGGER.error("Critical: invalid private key.", e);
        }
        return null;
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    /**
     * @return the current time in seconds since epoch
     */
    private static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}
