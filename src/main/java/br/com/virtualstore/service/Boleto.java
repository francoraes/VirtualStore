
package br.com.virtualstore.service;

public class Boleto implements MetodoPagamento {

    @Override
    public boolean pagar(double valor) {

        System.out.println("Gerando boleto...");
        System.out.println("Valor: R$ " + valor);

        /*
         * Simulação de pagamento aprovado.
         * Em um sistema real, o boleto só seria
         * aprovado depois da confirmação bancária.
         */
        return true;
    }

    @Override
    public String getNome() {
        return "Boleto";
    }
}