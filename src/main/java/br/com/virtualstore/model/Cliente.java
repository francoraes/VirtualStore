
package br.com.virtualstore.model;

/*
 * Representa um cliente cadastrado na loja.
 *
 * Herda de Usuario os atributos:
 * ID, nome e e-mail.
 */
public class Cliente extends Usuario {

    private String telefone;

    public Cliente(int id, String nome, String email, String telefone) {

        // Envia os dados compartilhados para o construtor de Usuario.
        super(id, nome, email);

        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
