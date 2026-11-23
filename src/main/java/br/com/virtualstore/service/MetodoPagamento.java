
package br.com.virtualstore.service;

/*
 * Interface que define o comportamento
 * obrigatório de todos os métodos de pagamento.
 *
 * Cada forma de pagamento terá sua própria
 * implementação do método pagar().
 */
public interface MetodoPagamento {

    boolean pagar(double valor);

    String getNome();
}
