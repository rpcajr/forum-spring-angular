package br.com.couto.forum.config.security;

import br.com.couto.forum.model.Usuario;
import br.com.couto.forum.repository.UsuarioRepository;
import br.com.couto.forum.service.security.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenService authTokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(httpServletRequest);

        if (authTokenService.validaToken(token)) {
            autenticarUsuario(token);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private String getToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    private void autenticarUsuario(String token) {
        Optional<Usuario> usuario = usuarioRepository.findById(authTokenService.getIdUsuario(token));
        if (usuario.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, usuario.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }


}
