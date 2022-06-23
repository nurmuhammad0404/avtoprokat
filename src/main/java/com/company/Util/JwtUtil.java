package com.company.Util;

import com.company.dto.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.exc.AppForbiddenException;
import com.company.exc.TokenNotValidException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "kalitso'z";

    public static String doEncode(Integer id, ProfileRole role, long minute) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(Integer.toString(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (minute * 60 * 1000)));
        jwtBuilder.setIssuer("jwt production");

        if (role != null) {
            jwtBuilder.claim("role", role);
        }

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static ProfileJwtDTO decode(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = jws.getBody();
        String id = claims.getId();
        String role = String.valueOf(claims.get("role"));
        return new ProfileJwtDTO(id, ProfileRole.valueOf(role));
    }


    public static Integer decodeAndGetId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = jws.getBody();
        String id = claims.getId();

        return Integer.parseInt(id);
    }



    public static String getIdFromHeader(HttpServletRequest request, ProfileRole... requiredRoles) {
        try {
            ProfileJwtDTO dto = (ProfileJwtDTO) request.getAttribute("profileJwtDTO");
            if (requiredRoles == null || requiredRoles.length == 0) {
                return dto.getId();
            }
            for (ProfileRole role : requiredRoles) {
                if (role.equals(dto.getRole())) {
                    return dto.getId();
                }
            }
        } catch (RuntimeException e) {
            throw new TokenNotValidException("Not Authorized");
        }
        throw new AppForbiddenException("Not acces");
    }

}
