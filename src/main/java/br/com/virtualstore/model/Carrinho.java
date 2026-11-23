
package br.com.virtualstore.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private Cliente cliente;
    private List<ItemCarrinho> itens;

    /*
     * Cada carrinho pertence a um cliente.
     * Inicialmente, ele começa vazio.
     */
    public Carrinho(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido!");
        }

        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    /*
     * Retorna uma cópia da lista.
     * Assim, outras classes não conseguem alterar
     * diretamente a lista interna do carrinho.
     */
    public List<ItemCarrinho> getItens() {
        return new ArrayList<>(itens);
    }

    /*
     * Procura um produto que já esteja no carrinho.
     */
    public ItemCarrinho buscarItem(int produtoId) {

        for (ItemCarrinho item : itens) {

            if (item.getProduto().getId() == produtoId) {
                return item;
            }
        }

        return null;
    }

    /*
     * Adiciona um produto.
     * Se ele já existir, apenas aumenta a quantidade.
     */
    public void adicionarItem(Produto produto, int quantidade) {

        ItemCarrinho itemExistente = buscarItem(produto.getId());

        if (itemExistente != null) {

            itemExistente.setQuantidade(
                    itemExistente.getQuantidade() + quantidade
            );

        } else {

            itens.add(new ItemCarrinho(produto, quantidade));
        }
    }

    /*
     * Remove completamente um produto do carrinho.
     */
    public void removerItem(int produtoId) {

        itens.removeIf(item ->
                item.getProduto().getId() == produtoId
        );
    }

    /*
     * Altera a quantidade de um produto já existente.
     */
    public boolean atualizarQuantidade(int produtoId, int quantidade) {

        ItemCarrinho item = buscarItem(produtoId);

        if (item == null) {
            return false;
        }

        item.setQuantidade(quantidade);

        return true;
    }

    /*
     * Soma o subtotal de todos os produtos.
     */
    public double calcularTotal() {

        double total = 0;

        for (ItemCarrinho item : itens) {
            total += item.getSubtotal();
        }

        return total;
    }

    /*
     * Esvazia o carrinho.
     * Será útil depois que um pedido for finalizado.
     */
    public void limpar() {
        itens.clear();
    }
}
