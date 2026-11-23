
package br.com.virtualstore.model;

/*
 * Classe base dos usuários da Virtual Store.
 *
 * Cliente e Administrador herdarão as informações
 * e comportamentos definidos nesta classe.
 *
 * abstract significa que não criaremos um Usuario diretamente.
 * Criaremos sempre um Cliente ou Administrador.
 */
public abstract class Usuario {

    private int id;
    private String nome;
    private String email;

    public Usuario(int id, String nome, String email) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Métodos de consulta.

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
