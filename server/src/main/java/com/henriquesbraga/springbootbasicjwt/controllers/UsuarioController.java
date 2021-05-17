package com.henriquesbraga.springbootbasicjwt.controllers;

import com.henriquesbraga.springbootbasicjwt.entities.Usuario;
import com.henriquesbraga.springbootbasicjwt.entities.dto.NovoUsuarioDTO;
import com.henriquesbraga.springbootbasicjwt.entities.dto.UsuarioDTO;
import com.henriquesbraga.springbootbasicjwt.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  // Create
  @RequestMapping(value = "/signup" ,method = RequestMethod.POST)
  public ResponseEntity<Void> insert(@Valid @RequestBody NovoUsuarioDTO objDto) {

    // Converte de DTO para User
    Usuario user = usuarioService.fromDTO(objDto);
    user = usuarioService.insert(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  // Read by id
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Usuario> findById(@PathVariable Long id) {
    Usuario obj = usuarioService.find(id);
    return ResponseEntity.ok().body(obj);
  }

  //Read all
  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public ResponseEntity<List<UsuarioDTO>> findAll(){
    List<Usuario> list = usuarioService.findall();
    //Retorna uma lista de DTO
    List<UsuarioDTO> listDto = list.stream().map((obj) -> new UsuarioDTO(obj)).collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  //Update
  @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objDto, @PathVariable Long id){
    Usuario user = usuarioService.fromDTO(objDto);
    user.setId(id);
    user = usuarioService.update(user);
    return ResponseEntity.noContent().build();
  }

  //Delete
  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    usuarioService.delete(id);
    return ResponseEntity.noContent().build();
  }


  //Public test
  @RequestMapping(value="/test", method=RequestMethod.GET)
  public ResponseEntity<String> publicTest(){
    String response = "Se Você estiver vendo esta mensagem, O servidor está online e esse endpoint é publico.";
    return ResponseEntity.ok().body(response);
  }

  @RequestMapping(value="/privatetest", method=RequestMethod.GET)
  public ResponseEntity<String> privateTest(){
    String response = "Se Você estiver vendo esta mensagem, O servidor está online e esse endpoint é privado.";
    return ResponseEntity.ok().body(response);
  }
  
  
}
