
package br.com.virtualstore.service;

public class Pix implements MetodoPagamento {

    @Override
    public boolean pagar(double valor) {

        System.out.println("Processando pagamento via PIX...");
        System.out.println("Valor: R$ " + valor);

        /*
         * Simulação de pagamento aprovado.
         * Retorne false para simular uma recusa.
         */
        return true;
    }

    @Override
    public String getNome() {
        return "PIX";
    }
}
