package com.assovio.holerize_api.domain.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.UsuarioDAO;
import com.assovio.holerize_api.domain.model.Usuario;

@Service
public class UsuarioService extends GenericService<Usuario, UsuarioDAO, Long> {

    public Optional<UserDetails> getUserDetailsByLogin(String login){
        return dao.findFirstUserDetailsByLogin(login);
    }

    public Optional<Usuario> getUsuarioByLoginOrEmail(String login, String email){
        return dao.findFirstUsuarioByLoginOrEmail(login, email);
    }

    public Optional<Usuario> getUsuarioByLogin(String login){
        return dao.findFirstByLogin(login);
    }

    public Optional<Usuario> findById(Long id) {
        return dao.findById(id);
    }
}
