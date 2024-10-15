package com.assovio.holerize_api.api.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.domain.service.UsuarioService;

import com.assovio.holerize_api.api.dto.response.UsuarioResponseDTO;
import com.assovio.holerize_api.domain.model.Usuario;
import lombok.AllArgsConstructor;


import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);


        Usuario usuario = usuarioOptional.get();
        UsuarioResponseDTO usuarioResponseDTO = toUsuarioResponseDTO(usuario);

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    private UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNome(usuario.getNome());
        usuarioResponseDTO.setLogin(usuario.getLogin());
        usuarioResponseDTO.setEmail(usuario.getEmail());
        usuarioResponseDTO.setRole(usuario.getRole());
        usuarioResponseDTO.setCreditos(usuario.getCreditos());
        usuarioResponseDTO.setProfileImage(usuario.getProfileImage());

        return usuarioResponseDTO;
    }
}
