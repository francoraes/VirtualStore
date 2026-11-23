
package br.com.virtualstore;

import br.com.virtualstore.menu.MenuPrincipal;
import br.com.virtualstore.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
         * =====================================
         * 1. INICIALIZANDO OS SERVIÇOS
         * =====================================
         *
         * Criamos apenas uma instância de cada
         * serviço para que todos os menus utilizem
         * os mesmos dados durante a execução.
         */

        Scanner scanner = new Scanner(System.in);

        ProdutoService produtoService = new ProdutoService();
        EstoqueService estoqueService = new EstoqueService();
        UsuarioService usuarioService = new UsuarioService();

        CarrinhoService carrinhoService =
                new CarrinhoService(produtoService, estoqueService);

        PedidoService pedidoService = new PedidoService();

        PagamentoService pagamentoService =
                new PagamentoService(estoqueService, carrinhoService);


        /*
         * =====================================
         * 2. CADASTRANDO ADMINISTRADOR INICIAL
         * =====================================
         *
         * Este administrador será utilizado
         * para acessar a área administrativa.
         */

        usuarioService.cadastrarAdministrador(
                "Administrador",
                "admin@virtualstore.com"
        );


        /*
         * =====================================
         * 3. CADASTRANDO PRODUTOS INICIAIS
         * =====================================
         *
         * Inserimos alguns produtos para que
         * o cliente já consiga testar a loja.
         */

        var notebook = produtoService.cadastrarProduto(
                "Notebook",
                "Notebook para jogos",
                3500.00,
                "Eletrônicos"
        );

        var mouse = produtoService.cadastrarProduto(
                "Mouse Gamer",
                "Mouse RGB",
                150.00,
                "Periféricos"
        );

        var teclado = produtoService.cadastrarProduto(
                "Teclado Mecânico",
                "Teclado com iluminação RGB",
                200.00,
                "Periféricos"
        );


        /*
         * =====================================
         * 4. ADICIONANDO ESTOQUE INICIAL
         * =====================================
         */

        estoqueService.cadastrarEstoque(notebook, 5);
        estoqueService.cadastrarEstoque(mouse, 10);
        estoqueService.cadastrarEstoque(teclado, 8);


        /*
         * =====================================
         * 5. INICIANDO O MENU PRINCIPAL
         * =====================================
         */

        MenuPrincipal menuPrincipal = new MenuPrincipal(
                scanner,
                usuarioService,
                produtoService,
                estoqueService,
                carrinhoService,
                pedidoService,
                pagamentoService
        );

        menuPrincipal.executar();

        scanner.close();
    }
}
