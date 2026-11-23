
package br.com.virtualstore.model;

/*
 * Representa um produto disponível na Virtual Store.
 *
 * Esta classe guarda as informações básicas do produto.
 * As operações de cadastro e pesquisa ficarão em ProdutoService.
 */
public class Produto {

    // Identificador único do produto.
    private int id;

    // Nome e descrição apresentados no catálogo.
    private String nome;
    private String descricao;

    // Preço de venda do produto.
    private double preco;

    // Categoria à qual o produto pertence.
    private String categoria;

    // Define se o produto está disponível para venda.
    private boolean ativo;

    /*
     * Construtor: cria um produto com suas informações iniciais.
     * Todo produto começa ativo.
     */
    public Produto(int id, String nome, String descricao,
                   double preco, String categoria) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (preco < 0) {
            throw new IllegalArgumentException("Preço inválido.");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.ativo = true;
    }

    // Métodos de consulta dos atributos (Getters).

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    /*
     * Métodos de atualização.
     * Eles permitem alterar os dados sem acessar os atributos diretamente.
     */

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço inválido.");
        }
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Desativa o produto sem removê-lo do catálogo.
    public void desativar() {
        this.ativo = false;
    }

    // Reativa um produto anteriormente desativado.
    public void ativar() {
        this.ativo = true;
    }
}
