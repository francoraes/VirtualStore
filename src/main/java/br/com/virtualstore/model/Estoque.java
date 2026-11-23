
package br.com.virtualstore.model;

/*
 * Representa o estoque de um produto.
 *
 * Cada objeto Estoque controla a quantidade disponível
 * de um produto específico.
 */
public class Estoque {

    private Produto produto;
    private int quantidade;

    public Estoque(Produto produto, int quantidade) {

        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido.");
        }

        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
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
     * Adiciona unidades ao estoque.
     * Não permite entradas com quantidades negativas ou zero.
     */
    public void adicionar(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }

        this.quantidade += quantidade;
    }

    /*
     * Remove unidades do estoque.
     * Impede a retirada de uma quantidade maior que a disponível.
     */
    public void remover(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }

        if (quantidade > this.quantidade) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente."
            );
        }

        this.quantidade -= quantidade;
    }
}
