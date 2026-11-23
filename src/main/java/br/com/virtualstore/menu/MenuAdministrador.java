
package br.com.virtualstore.menu;

import br.com.virtualstore.model.StatusPedido;
import br.com.virtualstore.service.*;

import java.util.Scanner;

public class MenuAdministrador {

    private Scanner scanner;

    private ProdutoService produtoService;
    private EstoqueService estoqueService;
    private PedidoService pedidoService;

    public MenuAdministrador(
            Scanner scanner,
            ProdutoService produtoService,
            EstoqueService estoqueService,
            PedidoService pedidoService) {

        this.scanner = scanner;
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
        this.pedidoService = pedidoService;
    }

    public void exibir() {

        int opcao;

        do {

            System.out.println("\n======= ÁREA DO ADMINISTRADOR =======");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Adicionar estoque");
            System.out.println("4 - Ativar produto");
            System.out.println("5 - Desativar produto");
            System.out.println("6 - Consultar pedidos");
            System.out.println("7 - Atualizar status de pedido");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1:
                    cadastrarProduto();
                    break;

                case 2:
                    listarProdutos();
                    break;

                case 3:
                    adicionarEstoque();
                    break;

                case 4:
                    alterarAtivacao(true);
                    break;

                case 5:
                    alterarAtivacao(false);
                    break;

                case 6:
                    listarPedidos();
                    break;

                case 7:
                    atualizarStatusPedido();
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    /*
     * Cadastra um novo produto no catálogo.
     */
    private void cadastrarProduto() {

        System.out.println("\n======= CADASTRAR PRODUTO =======");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = lerDouble();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        try {

            produtoService.cadastrarProduto(
                    nome, descricao, preco, categoria
            );

            System.out.println("Produto cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }

    /*
     * Mostra todos os produtos, inclusive os desativados.
     */
    private void listarProdutos() {

        System.out.println("\n======= CATÁLOGO =======");

        var produtos = produtoService.listarProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (var produto : produtos) {

            System.out.println(
                    "ID: " + produto.getId()
                            + " | Nome: " + produto.getNome()
                            + " | Preço: R$ " + produto.getPreco()
                            + " | Ativo: " + produto.isAtivo()
                            + " | Estoque: "
                            + estoqueService.consultarQuantidade(produto.getId())
            );
        }
    }

    /*
     * Adiciona unidades ao estoque de um produto.
     */
    private void adicionarEstoque() {

        listarProdutos();

        System.out.print("\nID do produto: ");
        int id = lerInteiro();

        System.out.print("Quantidade a adicionar: ");
        int quantidade = lerInteiro();

        try {

            estoqueService.adicionarEstoque(id, quantidade);

            System.out.println("Estoque atualizado!");

        } catch (IllegalArgumentException e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }

    /*
     * Ativa ou desativa um produto.
     */
    private void alterarAtivacao(boolean ativar) {

        listarProdutos();

        System.out.print("\nID do produto: ");
        int id = lerInteiro();

        boolean sucesso;

        if (ativar) {
            sucesso = produtoService.ativarProduto(id);
        } else {
            sucesso = produtoService.desativarProduto(id);
        }

        if (sucesso) {

            System.out.println(
                    ativar ? "Produto ativado!" : "Produto desativado!"
            );

        } else {

            System.out.println("Produto não encontrado.");
        }
    }

    /*
     * Exibe todos os pedidos realizados na loja.
     */
    private void listarPedidos() {

        System.out.println("\n======= PEDIDOS DA LOJA =======");

        var pedidos = pedidoService.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }

        for (var pedido : pedidos) {

            System.out.println(
                    "Pedido #" + pedido.getId()
                            + " | Cliente: " + pedido.getCliente().getNome()
                            + " | Total: R$ " + pedido.getValorTotal()
                            + " | Status: " + pedido.getStatus()
            );
        }
    }

    /*
     * Permite alterar o status de um pedido.
     */
    private void atualizarStatusPedido() {

        listarPedidos();

        System.out.print("\nID do pedido: ");
        int id = lerInteiro();

        System.out.println("\nEscolha o novo status:");

        StatusPedido[] status = StatusPedido.values();

        for (int i = 0; i < status.length; i++) {

            System.out.println(
                    (i + 1) + " - " + status[i]
            );
        }

        System.out.print("Escolha: ");
        int opcao = lerInteiro();

        if (opcao < 1 || opcao > status.length) {
            System.out.println("Status inválido!");
            return;
        }

        boolean sucesso = pedidoService.atualizarStatus(
                id, status[opcao - 1]
        );

        if (sucesso) {
            System.out.println("Status atualizado!");

        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    private int lerInteiro() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }

    private double lerDouble() {

        while (true) {

            try {
                String valor = scanner.nextLine().replace(",", ".");
                return Double.parseDouble(valor);

            } catch (NumberFormatException e) {
                System.out.print("Digite um preço válido: ");
            }
        }
    }
}
