package br.com.ecommerce;


import br.com.ecommerce.config.ApplicationConfig;
import br.com.ecommerce.domain.Usuario;
import br.com.ecommerce.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class EcommerceDataSetup {


    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private ApplicationConfig applicationConfig;

    @PostConstruct
    public void init() {


        passwordHash.initialize(applicationConfig.getHashAlgorithmParameterMap());

        var usuario = new Usuario();
        usuario.setNome("teste");
        usuario.setEmail("teste@t.com");
        usuario.setSenha(passwordHash.generate("teste123".toCharArray()));
        usuarioRepository.save(usuario);


    }

}
