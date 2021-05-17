package com.henriquesbraga.springbootbasicjwt.services;

import com.henriquesbraga.springbootbasicjwt.entities.Usuario;
import com.henriquesbraga.springbootbasicjwt.entities.dto.NovoUsuarioDTO;
import com.henriquesbraga.springbootbasicjwt.entities.dto.UsuarioDTO;
import com.henriquesbraga.springbootbasicjwt.entities.enums.Perfil;
import com.henriquesbraga.springbootbasicjwt.repositories.UsuarioRepository;

import com.henriquesbraga.springbootbasicjwt.security.UsuarioSpringSecurity;
import com.henriquesbraga.springbootbasicjwt.services.exceptions.AuthorizationException;
import com.henriquesbraga.springbootbasicjwt.services.exceptions.DataIntegrityException;
import com.henriquesbraga.springbootbasicjwt.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  //Create
  @Transactional
  public Usuario insert(Usuario obj){
    obj.setId(null);
    obj = usuarioRepository.save(obj);
    return obj;
  }

  //Read
  public Usuario find(Long id){

    //Se o cliente não for admin e não for o id dele, lançar exception
    UsuarioSpringSecurity user = UsuarioSpringSecurityService.authenticated();
    if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
      throw new AuthorizationException("Acesso negado.");
    }

    Optional<Usuario> obj = usuarioRepository.findById(id);
    return obj.orElseThrow(() -> new ObjectNotFoundException("User não encontrado."));
  }

  //Read All
  public List<Usuario> findall(){
    return usuarioRepository.findAll();
  }

  //Update
  public Usuario update(Usuario obj){
    Usuario newObj = find(obj.getId());
    updateData(newObj, obj);
    return usuarioRepository.save(newObj);
  }

  //Delete
  public void delete(Long id){
    find(id);
    try{
      usuarioRepository.deleteById(id);
    }
    catch(DataIntegrityException e){
      throw new DataIntegrityException("Não é possivel deletar por integridade de dados");
    }
  }

  private void updateData(Usuario newObj, Usuario obj){
    newObj.setNome(obj.getNome());
    newObj.setEmail(obj.getEmail());
  }

  //Converte de DTO para USER
  public Usuario fromDTO(NovoUsuarioDTO objDto){
    Usuario user = new Usuario(null, objDto.getEmail(), objDto.getNome(), passwordEncoder.encode(objDto.getSenha()));
    return user;
  }

  public Usuario fromDTO(UsuarioDTO objDto){
    Usuario user = new Usuario(null, objDto.getEmail(), objDto.getNome(), null);
    return user;
  }

}
