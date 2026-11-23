
package br.com.virtualstore.repository;

import br.com.virtualstore.model.Usuario;
import java.util.List;

/*
 * Define as operações necessárias para armazenar
 * e consultar os usuários do sistema.
 */
public interface UsuarioRepository {

    void salvar(Usuario usuario);

    Usuario buscarPorId(int id);

    Usuario buscarPorEmail(String email);

    List<Usuario> listarTodos();
}
