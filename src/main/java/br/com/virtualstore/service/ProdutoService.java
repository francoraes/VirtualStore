
package br.com.virtualstore.service;

import br.com.virtualstore.model.Produto;
import br.com.virtualstore.repository.ProdutoRepository;
import br.com.virtualstore.repository.impl.ProdutoRepositoryMemoria;

import java.util.List;

public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private int proximoId = 1;

    /*
     * Construtor padrão:
     * mantém a compatibilidade com o restante do projeto,
     * criando automaticamente o repositório em memória.
     */
    public ProdutoService() {
        this.produtoRepository = new ProdutoRepositoryMemoria();
    }

    /*
     * Construtor com injeção de dependência:
     * permite escolher qual repositório será utilizado.
     */
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(
            String nome,
            String descricao,
            double preco,
            String categoria) {

        Produto produto = new Produto(
                proximoId++,
                nome,
                descricao,
                preco,
                categoria
        );

        produtoRepository.salvar(produto);

        return produto;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.listarTodos();
    }

    public Produto buscarPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.buscarPorNome(nome);
    }

    public boolean desativarProduto(int id) {

        Produto produto = buscarPorId(id);

        if (produto == null) {
            return false;
        }

        produto.desativar();
        return true;
    }

    public boolean ativarProduto(int id) {

        Produto produto = buscarPorId(id);

        if (produto == null) {
            return false;
        }

        produto.ativar();
        return true;
    }

    public List<Produto> listarProdutosAtivos() {

        return produtoRepository.listarTodos()
                .stream()
                .filter(Produto::isAtivo)
                .toList();
    }
}
