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
import com.assovio.holerize_api.api.dto.response.UsuarioResponseDTO;
import com.assovio.holerize_api.domain.exceptions.BusinessException;
import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
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
    public ResponseEntity<UsuarioResponseDTO> login(@RequestBody @UsuarioLoginValid UsuarioRequestDTO requestDTO) {
        try {
            var optionalUsuario = usuarioService.getUsuarioByLoginOrEmail(requestDTO.getLogin(), requestDTO.getEmail());
            if (!optionalUsuario.isPresent())
                throw new RuntimeException();

            var usernamePassword = new UsernamePasswordAuthenticationToken(optionalUsuario.get().getLogin(),
                    requestDTO.getSenha());
            var auth = authenticationManager.authenticate(usernamePassword);
            Usuario usuario = (Usuario) auth.getPrincipal();
            UsuarioResponseDTO dto = usuarioAssembler.toDto(usuario);
            dto.setToken(tokenService.generateToken(usuario));

            if (dto.getToken() != null && !dto.getToken().isBlank())
                return new ResponseEntity<UsuarioResponseDTO>(dto, HttpStatus.OK);

        } catch (RuntimeException ex) {
            throw new NotAuthorizedException("Usuário ou senha inválidos!");
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("register")
    public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @UsuarioStoreValid UsuarioRequestDTO requestDTO)
            throws BusinessException {
        if (usuarioService.getUsuarioByLoginOrEmail(requestDTO.getLogin(), requestDTO.getEmail()).isPresent())
            throw new InvalidOperationException("Já existe um usuário cadastrado com este login");

        Usuario usuario = usuarioAssembler.toEntity(requestDTO);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario = usuarioService.save(usuario);
        UsuarioResponseDTO dto = usuarioAssembler.toDto(usuario);
        return new ResponseEntity<UsuarioResponseDTO>(dto, HttpStatus.CREATED);
    }

}
