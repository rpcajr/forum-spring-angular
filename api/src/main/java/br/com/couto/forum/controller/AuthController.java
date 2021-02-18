package br.com.couto.forum.controller;

import br.com.couto.forum.controller.dto.TokenDto;
import br.com.couto.forum.controller.form.UsuarioForm;
import br.com.couto.forum.service.security.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid UsuarioForm usuario) {
        UsernamePasswordAuthenticationToken userLogin = usuario.convertForToken();

        try {
            Authentication authentication = authenticationManager.authenticate(userLogin);
            String token = authTokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }


    }

}
