package vn.anhdoan.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Base64;

@Service
public class SecurityUtil {
    private JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${anhdoan.jwt.token-validity-in-seconds}")
    private long jwtExpiration;

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        // Thời gian hiện tại + thời gian trong biến jwtExpiration (1 ngày)
        Instant validity = now.plus(this.jwtExpiration, ChronoUnit.SECONDS);

        // Payload
        JwtClaimsSet claims = JwtClaimsSet.builder()
                // Thời gian tạo token
                .issuedAt(now)
                // Thời gian hết hạn token
                .expiresAt(validity)
                // Tên user
                .subject(authentication.getName())
                // Các thông tin liên quan đến user
                .claim("anhdoan", authentication)
                .build();

        // Header
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
