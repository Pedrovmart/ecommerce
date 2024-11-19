package repositories;

import br.com.ecommerce.domain.Produto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Produto> findAll() {
        return entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public void save(Produto produto) {
        if (produto.getId() == null) {
            entityManager.persist(produto);
        } else {
            entityManager.merge(produto);
        }
    }

    public void delete(Integer id) {
        Produto produto = entityManager.find(Produto.class, id);
        if (produto != null) {
            entityManager.remove(produto);
        }
    }
}
