package br.com.ecommerce.beans;


import br.com.ecommerce.dto.ProdutoDTO;
import br.com.ecommerce.services.ProdutoService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CarrinhoBean implements Serializable {

    @Inject
    private ProdutoService produtoService;

    // Método para listar os produtos do carrinho usando o DTO que vem do banco
    public List<ProdutoDTO> listarProdutosDoCarrinho() {
        return produtoService.listarProdutosAgrupadosDoCarrinho();
    }

    // Método para calcular o total do carrinho
    public double calcularTotal() {
        return produtoService.calcularTotalCarrinho();
    }

    // Método para remover um produto do carrinho
    public void removerProduto(Integer produtoId) {
        produtoService.removerProdutoDoCarrinho(produtoId);
    }

    // Método para finalizar a compra (implementação simulada)
    public String finalizarCompra() {
        return "compraFinalizada";  // Redireciona para uma página de confirmação
    }

    // Método para cancelar a compra (limpa o carrinho)
    public void cancelarCompra() {
        produtoService.limparCarrinho();
    }

    public List<ProdutoDTO> getProdutosAgrupadosNoCarrinho() {
        return produtoService.listarProdutosAgrupadosDoCarrinho();
    }

}
