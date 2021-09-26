package chat.chat.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import chat.chat.model.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    public String parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();

            return ((String) body.get("userId"));
        } catch (JwtException | ClassCastException e) {
            return "";
        }
    }

    public String generateToken(UserDTO userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("userId", userDetails.getId());

        String token = Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(SignatureAlgorithm.HS512, "secret").compact();

        return token;
    }
}
