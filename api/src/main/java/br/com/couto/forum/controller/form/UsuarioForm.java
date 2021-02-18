package br.com.couto.forum.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioForm {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken convertForToken(){
        return new UsernamePasswordAuthenticationToken(email,senha);
    }

}
