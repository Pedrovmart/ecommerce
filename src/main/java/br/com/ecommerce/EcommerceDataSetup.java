package br.com.ecommerce;

import br.com.ecommerce.config.ApplicationConfig;
import br.com.ecommerce.domain.Grupo;
import br.com.ecommerce.domain.Usuario;
import br.com.ecommerce.repositories.GrupoRepository;
import br.com.ecommerce.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Startup
@Singleton
public class EcommerceDataSetup {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private ApplicationConfig applicationConfig;

    @Inject
    private GrupoRepository grupoRepository;

    @PostConstruct
    public void init() {
        passwordHash.initialize(applicationConfig.getHashAlgorithmParameterMap());

        // Criar e persistir grupos
        Grupo adminGroup = new Grupo();
        adminGroup.setNome("admin");
        grupoRepository.save(adminGroup); // Persistir grupo admin

        Grupo userGroup = new Grupo();
        userGroup.setNome("usuario");
        grupoRepository.save(userGroup); // Persistir grupo usuario

        // Criar usuário administrador
        Usuario adminUser = new Usuario();
        adminUser.setNome("Administrador");
        adminUser.setEmail("admin@ecommerce.com");
        adminUser.setSenha(passwordHash.generate("admin123".toCharArray()));
        adminUser.getGrupos().add(adminGroup); // Associar grupo admin ao usuário
        usuarioRepository.save(adminUser); // Salvar administrador

        // Criar usuário comum
        Usuario regularUser = new Usuario();
        regularUser.setNome("Usuário Comum");
        regularUser.setEmail("usuario@ecommerce.com");
        regularUser.setSenha(passwordHash.generate("usuario123".toCharArray()));
        regularUser.getGrupos().add(userGroup); // Associar grupo usuario ao usuário
        usuarioRepository.save(regularUser); // Salvar usuário comum
    }
}
