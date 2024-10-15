package com.assovio.holerize_api.api.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;
import com.assovio.holerize_api.domain.model.Usuario;
import com.assovio.holerize_api.domain.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("/{id}/meusCreditos")
    public ResponseEntity<Integer> showMeusCreditosAtualizados(@PathVariable Long id) throws Exception {

        Optional<Usuario> usuarioOptional = this.usuarioService.getById(id);

        if (!usuarioOptional.isPresent())
            throw new RegisterNotFoundException("Usuario não encontrado!");

        return new ResponseEntity<>(usuarioOptional.get().getCreditos(), HttpStatus.OK);
    }

}
