package com.henriquesbraga.springbootbasicjwt.services.validations;

import com.henriquesbraga.springbootbasicjwt.controllers.exception.FieldMessage;
import com.henriquesbraga.springbootbasicjwt.entities.Usuario;
import com.henriquesbraga.springbootbasicjwt.entities.dto.NovoUsuarioDTO;
import com.henriquesbraga.springbootbasicjwt.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, NovoUsuarioDTO>{
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public void initialize(UsuarioInsert ann){
  }

  //Método que manipula a validação pela anotation
  @Override
  public boolean isValid(NovoUsuarioDTO objDto, ConstraintValidatorContext context){
    //Vai armazenar cada erro
    List<FieldMessage> list = new ArrayList<>();

    Usuario aux = usuarioRepository.findByEmail(objDto.getEmail());
    if(aux != null){
      list.add(new FieldMessage("email", "Email já cadastrado"));
    }
    return list.isEmpty();
  }

}
