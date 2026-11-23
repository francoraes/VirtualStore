
// ProdutoRepositoryMemoria.java
package br.com.virtualstore.repository.impl;

import br.com.virtualstore.model.Produto;
import br.com.virtualstore.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

/*
 * Esta classe implementa o repositório utilizando uma lista.
 *
 * Por enquanto, os produtos ficam armazenados na memória.
 * No futuro, poderemos criar outro repositório que utilize
 * um banco de dados sem precisar mudar as regras de negócio.
 */
public class ProdutoRepositoryMemoria implements ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public Produto buscarPorId(int id) {

        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }

        return null;
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {

        List<Produto> encontrados = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase()
                    .contains(nome.toLowerCase())) {

                encontrados.add(produto);
            }
        }

        return encontrados;
    }

    @Override
    public List<Produto> listarTodos() {

        /*
         * Retornamos uma cópia da lista para evitar que
         * outras classes alterem diretamente nossa coleção.
         */
        return new ArrayList<>(produtos);
    }
}
