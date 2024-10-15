package br.com.ecommerce.services;

import br.com.ecommerce.domain.Carrinho;
import br.com.ecommerce.domain.Produto;
import br.com.ecommerce.dto.ProdutoDTO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProdutoService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    // Adiciona produto ao carrinho usando query
    @Transactional
    public void adicionarProdutoAoCarrinho(Integer idProduto) {
        Produto produto = buscarProduto(idProduto);
        Carrinho carrinho = getCarrinhoGenerico();

        // Verifica se o produto já existe no carrinho
        Long count = em.createQuery("SELECT COUNT(p) FROM Carrinho c JOIN c.produtos p WHERE c.id = :carrinhoId AND p.id = :produtoId", Long.class)
                .setParameter("carrinhoId", carrinho.getId())
                .setParameter("produtoId", idProduto)
                .getSingleResult();

        if (count > 0) {
            // Produto já existe, incrementa a quantidade
            em.createQuery("UPDATE Produto p SET p.quantidade = p.quantidade + 1 WHERE p.id = :produtoId")
                    .setParameter("produtoId", idProduto)
                    .executeUpdate();
        } else {
            // Produto não existe, adiciona com quantidade 1
            produto.setQuantidade(1);
            carrinho.getProdutos().add(produto);
            em.merge(carrinho);
        }

        atualizarTotalCarrinho(carrinho);
    }

    // Remove produto do carrinho
    @Transactional
    public void removerProdutoDoCarrinho(Integer idProduto) {
        Produto produto = buscarProduto(idProduto);
        Carrinho carrinho = getCarrinhoGenerico();

        Long count = em.createQuery("SELECT COUNT(p) FROM Carrinho c JOIN c.produtos p WHERE c.id = :carrinhoId AND p.id = :produtoId", Long.class)
                .setParameter("carrinhoId", carrinho.getId())
                .setParameter("produtoId", idProduto)
                .getSingleResult();

        if (count > 0) {
            if (produto.getQuantidade() > 1) {
                em.createQuery("UPDATE Produto p SET p.quantidade = p.quantidade - 1 WHERE p.id = :produtoId")
                        .setParameter("produtoId", idProduto)
                        .executeUpdate();
            } else {
                em.createQuery("DELETE FROM Carrinho c WHERE c.id = :carrinhoId AND :produtoId MEMBER OF c.produtos")
                        .setParameter("carrinhoId", carrinho.getId())
                        .setParameter("produtoId", idProduto)
                        .executeUpdate();
            }
        }

        atualizarTotalCarrinho(carrinho);
    }

    // Calcula o total do carrinho
    @Transactional
    public void atualizarTotalCarrinho(Carrinho carrinho) {
        Double total = em.createQuery(
                        "SELECT SUM(p.preco * p.quantidade) FROM Carrinho c JOIN c.produtos p WHERE c.id = :carrinhoId", Double.class)
                .setParameter("carrinhoId", carrinho.getId())
                .getSingleResult();

        carrinho.setTotal(total != null ? total : 0.0);
        em.merge(carrinho);
    }

    // Retorna o carrinho genérico
    public Carrinho getCarrinhoGenerico() {
        List<Carrinho> carrinhos = em.createQuery("SELECT c FROM Carrinho c", Carrinho.class).getResultList();
        if (carrinhos.isEmpty()) {
            Carrinho novoCarrinho = new Carrinho();
            novoCarrinho.setTotal(0.0);
            em.persist(novoCarrinho);
            return novoCarrinho;
        }
        return carrinhos.get(0);
    }

    // Busca produto por ID
    public Produto buscarProduto(Integer id) {
        return em.find(Produto.class, id);
    }

    // Lista todos os produtos
    public List<Produto> listarProdutos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    // Limpa o carrinho
    @Transactional
    public void limparCarrinho() {
        Carrinho carrinho = getCarrinhoGenerico();
        carrinho.getProdutos().clear();
        carrinho.setTotal(0.0);
        em.merge(carrinho);
    }

    // Calcula o total do carrinho, baseado nos produtos persistidos no banco
    public double calcularTotalCarrinho() {
        Carrinho carrinho = getCarrinhoGenerico();

        // Calcula o total somando os preços dos produtos e suas quantidades
        Double total = em.createQuery(
                        "SELECT SUM(p.preco * p.quantidade) FROM Carrinho c JOIN c.produtos p WHERE c.id = :carrinhoId", Double.class)
                .setParameter("carrinhoId", carrinho.getId())
                .getSingleResult();

        return total != null ? total : 0.0;
    }

    public List<ProdutoDTO> listarProdutosAgrupadosDoCarrinho() {
        Carrinho carrinho = getCarrinhoGenerico();

        // Consulta para agrupar os produtos no carrinho e consolidar quantidades
        List<Object[]> resultados = em.createQuery(
                        "SELECT p.id, p.nome, p.preco, SUM(p.quantidade) AS quantidadeTotal " +
                                "FROM Carrinho c JOIN c.produtos p " +
                                "WHERE c.id = :carrinhoId " +
                                "GROUP BY p.id, p.nome, p.preco", Object[].class)
                .setParameter("carrinhoId", carrinho.getId())
                .getResultList();

        // Cria uma lista de ProdutoDTO para armazenar os dados consolidados
        List<ProdutoDTO> produtosAgrupados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Integer id = (Integer) resultado[0];
            String nome = (String) resultado[1];
            Double preco = (Double) resultado[2];
            Long quantidade = (Long) resultado[3];

            ProdutoDTO produtoDTO = new ProdutoDTO(id, nome, preco, quantidade.intValue());
            produtosAgrupados.add(produtoDTO);
        }

        return produtosAgrupados;
    }

    public List<String> listarCategorias() {
        return em.createQuery("SELECT DISTINCT p.categoria FROM Produto p", String.class).getResultList();
    }

    public List<Produto> filtrarProdutos(String categoria, Double precoMin, Double precoMax) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Produto p WHERE 1=1 ");

        if (categoria != null && !categoria.isEmpty()) {
            queryBuilder.append("AND p.categoria = :categoria ");
        }
        if (precoMin != null) {
            queryBuilder.append("AND p.preco >= :precoMin ");
        }
        if (precoMax != null) {
            queryBuilder.append("AND p.preco <= :precoMax ");
        }

        var query = em.createQuery(queryBuilder.toString(), Produto.class);

        if (categoria != null && !categoria.isEmpty()) {
            query.setParameter("categoria", categoria);
        }
        if (precoMin != null) {
            query.setParameter("precoMin", precoMin);
        }
        if (precoMax != null) {
            query.setParameter("precoMax", precoMax);
        }

        return query.getResultList();
    }



}
