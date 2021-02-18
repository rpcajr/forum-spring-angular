package br.com.couto.forum.service.security;

import br.com.couto.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AuthTokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date now = new Date();

        return Jwts.builder()
                .setIssuer("Api Forum Springboot")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean validaToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.secret)
                    .build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token){
        Claims body = Jwts.parserBuilder()
                .setSigningKey(this.secret)
                .build().parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }

}
