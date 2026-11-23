
package br.com.virtualstore.model;

public class ItemCarrinho {

    private Produto produto;
    private int quantidade;

    /*
     * Construtor do item.
     * Cada item precisa ter um produto válido
     * e uma quantidade maior que zero.
     */
    public ItemCarrinho(Produto produto, int quantidade) {

        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido!");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida!");
        }

        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /*
     * Permite atualizar a quantidade do produto.
     */
    public void setQuantidade(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida!");
        }

        this.quantidade = quantidade;
    }

    /*
     * Calcula o subtotal deste item.
     * Exemplo: 2 unidades de R$ 50 = R$ 100.
     */
    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }
}
