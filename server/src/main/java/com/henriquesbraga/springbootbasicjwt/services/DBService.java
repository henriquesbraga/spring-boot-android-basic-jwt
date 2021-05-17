package com.henriquesbraga.springbootbasicjwt.services;

import com.henriquesbraga.springbootbasicjwt.entities.Usuario;
import com.henriquesbraga.springbootbasicjwt.entities.enums.Perfil;
import com.henriquesbraga.springbootbasicjwt.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  BCryptPasswordEncoder pe;

  public void instantiateTesteDatabase(){
    Usuario admin = new Usuario(null, "admin@corp.com", "Admin User", pe.encode("admin"));
    admin.adicionaPerfil(Perfil.ADMIN);
    usuarioRepository.save(admin);
  }
}
