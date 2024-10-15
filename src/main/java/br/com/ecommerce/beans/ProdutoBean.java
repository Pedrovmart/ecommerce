package br.com.ecommerce.beans;

import br.com.ecommerce.domain.Produto;
import br.com.ecommerce.services.ProdutoService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProdutoBean implements Serializable {

    @Inject
    private ProdutoService produtoService;

    private List<Produto> produtos;
    private String filtroCategoria;
    private Double precoMin;
    private Double precoMax;
    private List<String> categorias;

    private List<Produto> produtosFiltrados;

    // Método para listar os produtos filtrados com base nos filtros aplicados
    public List<Produto> getProdutosFiltrados() {
        produtosFiltrados = produtoService.filtrarProdutos(filtroCategoria, precoMin, precoMax);
        return produtosFiltrados;
    }

    // Método para limpar o filtro
    public void limparFiltro() {
        this.filtroCategoria = null;
        this.precoMin = null;
        this.precoMax = null;
    }

    // Método para obter a lista de categorias únicas
    public List<String> getCategorias() {
        if (categorias == null) {
            categorias = produtoService.listarCategorias();
        }
        return categorias;
    }

    // Método para adicionar produto ao carrinho
    public void adicionarAoCarrinho(Integer produtoId) {
        produtoService.adicionarProdutoAoCarrinho(produtoId);
    }

    // Método para listar todos os produtos
    public List<Produto> getProdutos() {
        if (produtos == null) {
            produtos = produtoService.listarProdutos();
        }
        return produtos;
    }

    // Getters e Setters
    public String getFiltroCategoria() {
        return filtroCategoria;
    }

    public void setFiltroCategoria(String filtroCategoria) {
        this.filtroCategoria = filtroCategoria;
    }

    public Double getPrecoMin() {
        return precoMin;
    }

    public void setPrecoMin(Double precoMin) {
        this.precoMin = precoMin;
    }

    public Double getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(Double precoMax) {
        this.precoMax = precoMax;
    }
}
