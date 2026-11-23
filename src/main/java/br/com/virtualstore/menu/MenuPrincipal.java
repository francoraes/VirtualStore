
package br.com.virtualstore.menu;

import br.com.virtualstore.model.Cliente;
import br.com.virtualstore.model.Usuario;
import br.com.virtualstore.service.*;

import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner;

    private UsuarioService usuarioService;
    private MenuCliente menuCliente;
    private MenuAdministrador menuAdministrador;

    public MenuPrincipal(
            Scanner scanner,
            UsuarioService usuarioService,
            ProdutoService produtoService,
            EstoqueService estoqueService,
            CarrinhoService carrinhoService,
            PedidoService pedidoService,
            PagamentoService pagamentoService) {

        this.scanner = scanner;
        this.usuarioService = usuarioService;

        this.menuCliente = new MenuCliente(
                scanner,
                produtoService,
                carrinhoService,
                pedidoService,
                pagamentoService
        );

        this.menuAdministrador = new MenuAdministrador(
                scanner,
                produtoService,
                estoqueService,
                pedidoService
        );
    }

    public void executar() {

        int opcao;

        do {

            System.out.println("\n================================");
            System.out.println("        VIRTUAL STORE");
            System.out.println("================================");
            System.out.println("1 - Área do cliente");
            System.out.println("2 - Área do administrador");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1:
                    acessarCliente();
                    break;

                case 2:
                    acessarAdministrador();
                    break;

                case 0:
                    System.out.println("Obrigado por utilizar a Virtual Store!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    /*
     * Permite que o cliente faça cadastro ou login.
     */
    private void acessarCliente() {

        System.out.println("\n======= ÁREA DO CLIENTE =======");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Entrar");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        if (opcao == 1) {

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            try {

                Cliente cliente = usuarioService.cadastrarCliente(
                        nome, email, telefone
                );

                System.out.println("Cadastro realizado!");

                menuCliente.exibir(cliente);

            } catch (IllegalArgumentException e) {

                System.out.println("Erro: " + e.getMessage());
            }

        } else if (opcao == 2) {

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            Usuario usuario = usuarioService.buscarPorEmail(email);

            if (usuario instanceof Cliente) {

                menuCliente.exibir((Cliente) usuario);

            } else {

                System.out.println("Cliente não encontrado.");
            }

        } else if (opcao != 0) {

            System.out.println("Opção inválida!");
        }
    }

    /*
     * Acesso simplificado do administrador por e-mail.
     */
    private void acessarAdministrador() {

        System.out.print("\nE-mail do administrador: ");
        String email = scanner.nextLine();

        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (usuario instanceof br.com.virtualstore.model.Administrador) {

            System.out.println("Acesso autorizado!");

            menuAdministrador.exibir();

        } else {

            System.out.println("Administrador não encontrado.");
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
}
