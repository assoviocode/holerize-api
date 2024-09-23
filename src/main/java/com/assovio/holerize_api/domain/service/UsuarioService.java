package com.assovio.holerize_api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.UsuarioDAO;
import com.assovio.holerize_api.domain.model.Usuario;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAO dao;

    public Usuario save(Usuario usuario){
        return dao.save(usuario);
    }

    public Optional<UserDetails> getUserDetailsByLogin(String login){
        return dao.findFirstUserDetailsByLogin(login);
    }

    public Optional<Usuario> getUsuarioByLoginOrEmail(String login, String email){
        return dao.findFirstUsuarioByLoginOrEmail(login, email);
    }
}
