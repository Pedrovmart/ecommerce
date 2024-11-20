package br.com.ecommerce.repositories;

import br.com.ecommerce.domain.Grupo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class GrupoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Grupo grupo) {
        entityManager.persist(grupo);
    }
}
