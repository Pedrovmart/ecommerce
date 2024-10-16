package br.com.ecommerce.security;

import br.com.ecommerce.config.ApplicationConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
@Startup
public class SecurityDataSetup {

    @Resource
    private DataSource dataSource; // DataSource padrão

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private ApplicationConfig applicationConfig;

    @PostConstruct
    public void init() {
        // Inicializa o password hash com os parâmetros definidos na ApplicationConfig
        passwordHash.initialize(applicationConfig.getHashAlgorithmParameterMap());


    }

    @PreDestroy
    public void destroy() {
        // Remove as tabelas ao destruir o bean
        try {
            executeUpdate(dataSource, "DROP TABLE IF EXISTS usuario");
            executeUpdate(dataSource, "DROP TABLE IF EXISTS usuario_grupo");
        } catch (Exception e) {
            // Ignora silenciosamente qualquer erro
        }
    }

    private void executeUpdate(DataSource dataSource, String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
