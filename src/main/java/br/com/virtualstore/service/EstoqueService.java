
package br.com.virtualstore.service;

import br.com.virtualstore.model.Estoque;
import br.com.virtualstore.model.Produto;

import java.util.ArrayList;
import java.util.List;

/*
 * Centraliza o controle de estoque da Virtual Store.
 *
 * É responsável por registrar produtos no estoque,
 * consultar quantidades e controlar entradas e saídas.
 */
public class EstoqueService {

    private final List<Estoque> estoques = new ArrayList<>();

    /*
     * Registra um produto no estoque.
     * Cada produto terá um único registro de estoque.
     */
    public void cadastrarEstoque(Produto produto, int quantidade) {

        if (buscarEstoque(produto.getId()) != null) {
            throw new IllegalArgumentException(
                    "Produto já possui estoque cadastrado."
            );
        }

        Estoque estoque = new Estoque(produto, quantidade);
        estoques.add(estoque);
    }

    /*
     * Localiza o estoque correspondente ao ID do produto.
     */
    private Estoque buscarEstoque(int produtoId) {

        for (Estoque estoque : estoques) {

            if (estoque.getProduto().getId() == produtoId) {
                return estoque;
            }
        }

        return null;
    }

    // Consulta a quantidade disponível de um produto.
    public int consultarQuantidade(int produtoId) {

        Estoque estoque = buscarEstoque(produtoId);

        if (estoque == null) {
            return 0;
        }

        return estoque.getQuantidade();
    }

    // Registra uma entrada de mercadorias.
    public void adicionarEstoque(int produtoId, int quantidade) {

        Estoque estoque = buscarEstoque(produtoId);

        if (estoque == null) {
            throw new IllegalArgumentException(
                    "Estoque não encontrado."
            );
        }

        estoque.adicionar(quantidade);
    }

    // Registra uma saída de mercadorias.
    public void removerEstoque(int produtoId, int quantidade) {

        Estoque estoque = buscarEstoque(produtoId);

        if (estoque == null) {
            throw new IllegalArgumentException(
                    "Estoque não encontrado."
            );
        }

        estoque.remover(quantidade);
    }

    // Lista todos os registros de estoque.
    public List<Estoque> listarEstoques() {
        return new ArrayList<>(estoques);
    }
}
