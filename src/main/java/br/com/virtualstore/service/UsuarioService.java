
package br.com.virtualstore.service;

import br.com.virtualstore.model.*;
import br.com.virtualstore.repository.UsuarioRepository;
import br.com.virtualstore.repository.impl.UsuarioRepositoryMemoria;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private int proximoId = 1;

    /*
     * Construtor padrão: utiliza armazenamento em memória.
     */
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepositoryMemoria();
    }

    /*
     * Permite receber outro tipo de repositório no futuro.
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private boolean emailExiste(String email) {
        return usuarioRepository.buscarPorEmail(email) != null;
    }

    public Cliente cadastrarCliente(
            String nome,
            String email,
            String telefone) {

        if (emailExiste(email)) {
            throw new IllegalArgumentException(
                    "Já existe um usuário com esse e-mail."
            );
        }

        Cliente cliente = new Cliente(
                proximoId++,
                nome,
                email,
                telefone
        );

        usuarioRepository.salvar(cliente);

        return cliente;
    }

    public Administrador cadastrarAdministrador(
            String nome,
            String email) {

        if (emailExiste(email)) {
            throw new IllegalArgumentException(
                    "Já existe um usuário com esse e-mail."
            );
        }

        Administrador administrador = new Administrador(
                proximoId++,
                nome,
                email
        );

        usuarioRepository.salvar(administrador);

        return administrador;
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.buscarPorId(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public List<Cliente> listarClientes() {

        return usuarioRepository.listarTodos()
                .stream()
                .filter(usuario -> usuario instanceof Cliente)
                .map(usuario -> (Cliente) usuario)
                .collect(Collectors.toList());
    }

    public List<Administrador> listarAdministradores() {

        return usuarioRepository.listarTodos()
                .stream()
                .filter(usuario -> usuario instanceof Administrador)
                .map(usuario -> (Administrador) usuario)
                .collect(Collectors.toList());
    }
}
