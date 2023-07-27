package uz.pdp.appnewsiteroles.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appnewsiteroles.entity.Role;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class JwtProwider {
    long expireTime = 36_000_000;
    String kalitSuz = "BuTokenniMaxfiySuziHechKimBilmasin1234567890";
    public  String generateToken(String username ,  Role roles){
        Date expiredDate = new Date(System.currentTimeMillis()+expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .claim("roles" , roles.toString())
                .signWith(SignatureAlgorithm.HS512, kalitSuz)
                .compact();


        System.out.println(token);
        return token;
    }


    public boolean validateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(kalitSuz)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

    public String getUserNameFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(kalitSuz)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
