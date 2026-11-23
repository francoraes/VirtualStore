
package br.com.virtualstore.model;

/*
 * Representa um administrador da Virtual Store.
 *
 * Por enquanto, possui os dados básicos de Usuario.
 * Posteriormente adicionaremos permissões administrativas.
 */
public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email) {

        // Reutiliza o construtor da classe Usuario.
        super(id, nome, email);
    }
}
