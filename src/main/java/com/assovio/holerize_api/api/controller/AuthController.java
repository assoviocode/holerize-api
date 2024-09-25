package com.assovio.holerize_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.UsuarioAssembler;
import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;
import com.assovio.holerize_api.domain.exceptions.BusinessException;
import com.assovio.holerize_api.domain.exceptions.InvalidOperation;
import com.assovio.holerize_api.domain.exceptions.NotAuthorizedException;
import com.assovio.holerize_api.domain.model.Usuario;
import com.assovio.holerize_api.domain.service.TokenService;
import com.assovio.holerize_api.domain.service.UsuarioService;
import com.assovio.holerize_api.domain.validator.usuario.UsuarioLoginValid;
import com.assovio.holerize_api.domain.validator.usuario.UsuarioStoreValid;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    
    private AuthenticationManager authenticationManager;
    private UsuarioService usuarioService;
    private TokenService tokenService;
    UsuarioAssembler usuarioAssembler;
    PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @UsuarioLoginValid UsuarioRequestDTO requestDTO) {
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getSenha());
            var auth = authenticationManager.authenticate(usernamePassword);
            Usuario usuario = (Usuario) auth.getPrincipal();
            var token = tokenService.generateToken(usuario);

            if (token != null && !token.isBlank())
                return ResponseEntity.ok(token);

        } catch (RuntimeException ex){
            throw new NotAuthorizedException("Usuário ou senha inválidos!");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
    @PostMapping("register")
    public ResponseEntity<?> store(@RequestBody @UsuarioStoreValid UsuarioRequestDTO requestDTO) throws BusinessException {
        if (usuarioService.getUsuarioByLoginOrEmail(requestDTO.getLogin(), requestDTO.getEmail()).isPresent())
            throw new InvalidOperation("Já existe um usuário cadastrado com este login");

        Usuario usuario = usuarioAssembler.toEntity(requestDTO);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
