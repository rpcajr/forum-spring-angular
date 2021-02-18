package br.com.couto.forum.service.security;

import br.com.couto.forum.model.Usuario;
import br.com.couto.forum.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(s);
        if(usuario.isPresent()){
            return usuario.get();
        }

        throw new UsernameNotFoundException("Dados inválidos!");
    }

}
