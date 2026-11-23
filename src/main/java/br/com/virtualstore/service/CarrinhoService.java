
package br.com.virtualstore.service;

import br.com.virtualstore.model.Carrinho;
import br.com.virtualstore.model.Cliente;
import br.com.virtualstore.model.ItemCarrinho;
import br.com.virtualstore.model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarrinhoService {

    private Map<Integer, Carrinho> carrinhos;

    private ProdutoService produtoService;
    private EstoqueService estoqueService;

    /*
     * O construtor recebe os serviços de produtos e estoque.
     *
     * Assim, o carrinho consegue consultar os produtos
     * cadastrados e verificar a quantidade disponível.
     */
    public CarrinhoService(
            ProdutoService produtoService,
            EstoqueService estoqueService) {

        this.produtoService = produtoService;
        this.estoqueService = estoqueService;

        this.carrinhos = new HashMap<>();
    }

    /*
     * Busca o carrinho de um cliente.
     *
     * Caso ainda não exista, cria um novo carrinho.
     * Utilizamos o ID do cliente como chave do Map.
     */
    public Carrinho obterCarrinho(Cliente cliente) {

        return carrinhos.computeIfAbsent(
                cliente.getId(),
                id -> new Carrinho(cliente)
        );
    }

    /*
     * Adiciona um produto ao carrinho do cliente.
     */
    public void adicionarProduto(
            Cliente cliente,
            int produtoId,
            int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero!"
            );
        }

        // Busca o produto pelo ID.
        Produto produto = produtoService.buscarPorId(produtoId);

        if (produto == null) {
            throw new IllegalArgumentException(
                    "Produto não encontrado!"
            );
        }

        // Impede a compra de produtos desativados.
        if (!produto.isAtivo()) {
            throw new IllegalArgumentException(
                    "Este produto não está disponível!"
            );
        }

        // Recupera ou cria o carrinho do cliente.
        Carrinho carrinho = obterCarrinho(cliente);

        // Verifica se o produto já está no carrinho.
        ItemCarrinho itemExistente =
                carrinho.buscarItem(produtoId);

        int quantidadeAtual = 0;

        if (itemExistente != null) {
            quantidadeAtual = itemExistente.getQuantidade();
        }

        // Soma a quantidade que já existe com a nova.
        int novaQuantidade = quantidadeAtual + quantidade;

        // Consulta o estoque sem descontar nada dele.
        int estoqueDisponivel =
                estoqueService.consultarQuantidade(produtoId);

        if (novaQuantidade > estoqueDisponivel) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente! Disponível: "
                            + estoqueDisponivel
            );
        }

        // Se todas as validações passaram, adiciona o produto.
        carrinho.adicionarItem(produto, quantidade);
    }

    /*
     * Remove um produto do carrinho.
     */
    public void removerProduto(Cliente cliente, int produtoId) {

        Carrinho carrinho = obterCarrinho(cliente);

        carrinho.removerItem(produtoId);
    }

    /*
     * Atualiza a quantidade de um produto.
     */
    public void atualizarQuantidade(
            Cliente cliente,
            int produtoId,
            int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero!"
            );
        }

        Carrinho carrinho = obterCarrinho(cliente);

        ItemCarrinho item = carrinho.buscarItem(produtoId);

        if (item == null) {
            throw new IllegalArgumentException(
                    "Produto não encontrado no carrinho!"
            );
        }

        int estoqueDisponivel =
                estoqueService.consultarQuantidade(produtoId);

        if (quantidade > estoqueDisponivel) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente! Disponível: "
                            + estoqueDisponivel
            );
        }

        carrinho.atualizarQuantidade(produtoId, quantidade);
    }

    /*
     * Lista os produtos do carrinho.
     */
    public List<ItemCarrinho> listarItens(Cliente cliente) {

        Carrinho carrinho = obterCarrinho(cliente);

        return carrinho.getItens();
    }

    /*
     * Calcula o valor total da compra.
     */
    public double calcularTotal(Cliente cliente) {

        Carrinho carrinho = obterCarrinho(cliente);

        return carrinho.calcularTotal();
    }

    /*
     * Limpa o carrinho do cliente.
     */
    public void limparCarrinho(Cliente cliente) {

        Carrinho carrinho = obterCarrinho(cliente);

        carrinho.limpar();
    }
}
