package com.assovio.holerize_api.domain.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.assovio.holerize_api.domain.model.Usuario;


public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
    
    Optional<UserDetails> findFirstUserDetailsByLogin(String login);

    Optional<Usuario> findFirstUsuarioByLoginOrEmail(String login, String email);

    Optional<Usuario> findFirstByLogin(String login);
}
