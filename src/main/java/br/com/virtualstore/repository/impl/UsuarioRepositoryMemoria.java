
package br.com.virtualstore.repository.impl;

import br.com.virtualstore.model.Usuario;
import br.com.virtualstore.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryMemoria implements UsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorId(int id) {

        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public Usuario buscarPorEmail(String email) {

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}
