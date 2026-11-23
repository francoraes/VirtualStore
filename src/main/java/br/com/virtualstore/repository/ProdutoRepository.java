
// ProdutoRepository.java
package br.com.virtualstore.repository;

import br.com.virtualstore.model.Produto;
import java.util.List;

/*
 * Esta interface define as operações que qualquer repositório
 * de produtos precisa oferecer.
 *
 * Assim, o restante do sistema não precisa saber onde os
 * produtos estão sendo armazenados.
 */
public interface ProdutoRepository {

    void salvar(Produto produto);

    Produto buscarPorId(int id);

    List<Produto> buscarPorNome(String nome);

    List<Produto> listarTodos();
}
