package br.com.ecommerce.repositories;


import br.com.ecommerce.domain.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Usuario> findAll() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void save(Usuario usuario) {
        entityManager.persist(usuario);
    }

    public Usuario find(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    public void update(Usuario usuario) {
        entityManager.merge(usuario);
    }

    public void delete(Long id) {
        Usuario usuario = find(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }
}
