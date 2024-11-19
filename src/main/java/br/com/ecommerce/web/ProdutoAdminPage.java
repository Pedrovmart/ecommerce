package br.com.ecommerce.web;

import br.com.ecommerce.domain.Produto;
import repositories.ProdutoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProdutoAdminPage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ProdutoRepository produtoRepository;

    private List<Produto> produtos;

    @PostConstruct
    public void init() {
        produtos = produtoRepository.findAll();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void edit(Produto produto) {
        produtoRepository.save(produto);
    }

    public void delete(Produto produto) {
        produtoRepository.delete(produto.getId());
        produtos.remove(produto);
    }
}
