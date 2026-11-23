
package br.com.virtualstore.menu;

import br.com.virtualstore.model.Cliente;
import br.com.virtualstore.model.ItemCarrinho;
import br.com.virtualstore.model.Pedido;
import br.com.virtualstore.service.*;

import java.util.Scanner;

public class MenuCliente {

    private Scanner scanner;

    private ProdutoService produtoService;
    private CarrinhoService carrinhoService;
    private PedidoService pedidoService;
    private PagamentoService pagamentoService;

    public MenuCliente(
            Scanner scanner,
            ProdutoService produtoService,
            CarrinhoService carrinhoService,
            PedidoService pedidoService,
            PagamentoService pagamentoService) {

        this.scanner = scanner;
        this.produtoService = produtoService;
        this.carrinhoService = carrinhoService;
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
    }

    /*
     * Exibe o menu do cliente repetidamente
     * até que ele escolha voltar.
     */
    public void exibir(Cliente cliente) {

        int opcao;

        do {
            System.out.println("\n======= ÁREA DO CLIENTE =======");
            System.out.println("Olá, " + cliente.getNome() + "!");
            System.out.println("1 - Ver produtos");
            System.out.println("2 - Adicionar produto ao carrinho");
            System.out.println("3 - Visualizar carrinho");
            System.out.println("4 - Remover produto do carrinho");
            System.out.println("5 - Atualizar quantidade");
            System.out.println("6 - Finalizar compra");
            System.out.println("7 - Histórico de pedidos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1:
                    listarProdutos();
                    break;

                case 2:
                    adicionarProduto(cliente);
                    break;

                case 3:
                    visualizarCarrinho(cliente);
                    break;

                case 4:
                    removerProduto(cliente);
                    break;

                case 5:
                    atualizarQuantidade(cliente);
                    break;

                case 6:
                    finalizarCompra(cliente);
                    break;

                case 7:
                    listarHistorico(cliente);
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void listarProdutos() {

        System.out.println("\n======= PRODUTOS DISPONÍVEIS =======");

        var produtos = produtoService.listarProdutosAtivos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto disponível.");
            return;
        }

        for (var produto : produtos) {

            System.out.println(
                    "ID: " + produto.getId()
                            + " | " + produto.getNome()
                            + " | R$ " + produto.getPreco()
            );
        }
    }

    private void adicionarProduto(Cliente cliente) {

        listarProdutos();

        System.out.print("\nID do produto: ");
        int id = lerInteiro();

        System.out.print("Quantidade: ");
        int quantidade = lerInteiro();

        try {
            carrinhoService.adicionarProduto(cliente, id, quantidade);
            System.out.println("Produto adicionado ao carrinho!");

        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível adicionar: " + e.getMessage());
        }
    }

    private void visualizarCarrinho(Cliente cliente) {

        System.out.println("\n======= SEU CARRINHO =======");

        var itens = carrinhoService.listarItens(cliente);

        if (itens.isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
            return;
        }

        for (ItemCarrinho item : itens) {

            System.out.println(
                    item.getProduto().getNome()
                            + " | Quantidade: " + item.getQuantidade()
                            + " | Subtotal: R$ " + item.getSubtotal()
            );
        }

        System.out.println(
                "TOTAL: R$ " + carrinhoService.calcularTotal(cliente)
        );
    }

    private void removerProduto(Cliente cliente) {

        visualizarCarrinho(cliente);

        System.out.print("ID do produto que deseja remover: ");
        int id = lerInteiro();

        carrinhoService.removerProduto(cliente, id);

        System.out.println("Operação de remoção realizada.");
    }

    private void atualizarQuantidade(Cliente cliente) {

        visualizarCarrinho(cliente);

        System.out.print("ID do produto: ");
        int id = lerInteiro();

        System.out.print("Nova quantidade: ");
        int quantidade = lerInteiro();

        try {
            carrinhoService.atualizarQuantidade(cliente, id, quantidade);
            System.out.println("Quantidade atualizada!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void finalizarCompra(Cliente cliente) {

        visualizarCarrinho(cliente);

        if (carrinhoService.listarItens(cliente).isEmpty()) {
            return;
        }

        System.out.println("\n======= FORMA DE PAGAMENTO =======");
        System.out.println("1 - PIX");
        System.out.println("2 - Cartão");
        System.out.println("3 - Boleto");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        MetodoPagamento metodo;

        switch (opcao) {

            case 1:
                metodo = new Pix();
                break;

            case 2:
                metodo = new Cartao();
                break;

            case 3:
                metodo = new Boleto();
                break;

            default:
                System.out.println("Forma de pagamento inválida!");
                return;
        }

        try {

            Pedido pedido = pedidoService.criarPedido(
                    carrinhoService.obterCarrinho(cliente)
            );

            pagamentoService.processarPagamento(pedido, metodo);

        } catch (IllegalArgumentException | IllegalStateException e) {

            System.out.println("Não foi possível finalizar: " + e.getMessage());
        }
    }

    private void listarHistorico(Cliente cliente) {

        System.out.println("\n======= HISTÓRICO DE PEDIDOS =======");

        var pedidos = pedidoService.listarPedidosCliente(cliente.getId());

        if (pedidos.isEmpty()) {
            System.out.println("Você ainda não realizou pedidos.");
            return;
        }

        for (Pedido pedido : pedidos) {

            System.out.println(
                    "Pedido #" + pedido.getId()
                            + " | Total: R$ " + pedido.getValorTotal()
                            + " | Status: " + pedido.getStatus()
            );
        }
    }

    /*
     * Lê números sem deixar o Scanner travado
     * caso o usuário digite letras por engano.
     */
    private int lerInteiro() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
