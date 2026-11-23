
package br.com.virtualstore.service;

import br.com.virtualstore.model.Pedido;
import br.com.virtualstore.model.ItemPedido;
import br.com.virtualstore.model.StatusPedido;

public class PagamentoService {

    private EstoqueService estoqueService;
    private CarrinhoService carrinhoService;

    /*
     * Recebemos os serviços de estoque e carrinho
     * para poder atualizar os dados após o pagamento.
     */
    public PagamentoService(
            EstoqueService estoqueService,
            CarrinhoService carrinhoService) {

        this.estoqueService = estoqueService;
        this.carrinhoService = carrinhoService;
    }

    /*
     * Processa o pagamento de um pedido.
     */
    public boolean processarPagamento(
            Pedido pedido,
            MetodoPagamento metodoPagamento) {

        if (pedido == null || metodoPagamento == null) {
            throw new IllegalArgumentException(
                    "Pedido ou método de pagamento inválido!"
            );
        }

        /*
         * Impede pagar novamente um pedido já pago
         * ou que tenha sido cancelado.
         */
        if (pedido.getStatus() == StatusPedido.PAGO
                || pedido.getStatus() == StatusPedido.CANCELADO) {

            throw new IllegalStateException(
                    "Este pedido não pode receber outro pagamento!"
            );
        }

        /*
         * Antes de cobrar, verificamos se todos os
         * produtos ainda possuem estoque suficiente.
         *
         * Isso evita descontar apenas parte dos itens.
         */
        for (ItemPedido item : pedido.getItens()) {

            int estoqueDisponivel =
                    estoqueService.consultarQuantidade(item.getProdutoId());

            if (item.getQuantidade() > estoqueDisponivel) {

                throw new IllegalStateException(
                        "Estoque insuficiente para: "
                                + item.getNomeProduto()
                );
            }
        }

        /*
         * Utilizamos a estratégia escolhida para
         * realizar o pagamento.
         */
        System.out.println("\n======= PAGAMENTO =======");

        System.out.println(
                "Método escolhido: " + metodoPagamento.getNome()
        );

        boolean aprovado = metodoPagamento.pagar(
                pedido.getValorTotal()
        );

        /*
         * Se o pagamento for recusado,
         * não alteramos o estoque nem limpamos o carrinho.
         */
        if (!aprovado) {

            pedido.setStatus(StatusPedido.PAGAMENTO_RECUSADO);

            System.out.println("Pagamento recusado!");

            return false;
        }

        /*
         * Se aprovado, descontamos os produtos do estoque.
         */
        for (ItemPedido item : pedido.getItens()) {

            estoqueService.removerEstoque(
                    item.getProdutoId(),
                    item.getQuantidade()
            );
        }

        /*
         * Atualizamos o estado do pedido.
         */
        pedido.setStatus(StatusPedido.PAGO);

        /*
         * Limpamos o carrinho do cliente.
         */
        carrinhoService.limparCarrinho(
                pedido.getCliente()
        );

        System.out.println("Pagamento aprovado!");
        System.out.println("Pedido finalizado com sucesso!");

        return true;
    }
}
