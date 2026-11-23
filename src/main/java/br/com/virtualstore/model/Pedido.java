
package br.com.virtualstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;

    private double valorTotal;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    /*
     * Construtor do pedido.
     *
     * Recebe as informações da compra e registra
     * a data em que o pedido foi criado.
     */
    public Pedido(
            int id,
            Cliente cliente,
            List<ItemPedido> itens) {

        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens);

        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.dataCriacao = LocalDateTime.now();

        calcularValorTotal();
    }

    /*
     * Soma os subtotais dos produtos para encontrar
     * o valor total do pedido.
     */
    private void calcularValorTotal() {

        valorTotal = 0;

        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    /*
     * Permite atualizar o estado do pedido.
     * Será utilizado posteriormente pelo pagamento.
     */
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}
