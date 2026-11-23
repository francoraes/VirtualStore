
package br.com.virtualstore.service;

public class Cartao implements MetodoPagamento {

    @Override
    public boolean pagar(double valor) {

        System.out.println("Processando pagamento via cartão...");
        System.out.println("Valor: R$ " + valor);

        /*
         * Simulação de aprovação do cartão.
         */
        return true;
    }

    @Override
    public String getNome() {
        return "Cartão";
    }
}
