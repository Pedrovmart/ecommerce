package br.com.ecommerce.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "carrinho_produto",
            joinColumns = @JoinColumn(name = "carrinho_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos = new ArrayList<>(); // Inicializa a lista de produtos

    private Double total = 0.0; // Inicializa o total do carrinho

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
