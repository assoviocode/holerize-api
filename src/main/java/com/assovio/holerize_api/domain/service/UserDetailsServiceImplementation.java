package com.assovio.holerize_api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    
    @Autowired
    private UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = service.getUserDetailsByLogin(login);
        if (user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException("Usuário não encontrado");
    }

}
