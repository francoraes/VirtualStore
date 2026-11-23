
package br.com.virtualstore.service;

import br.com.virtualstore.model.*;
import br.com.virtualstore.repository.PedidoRepository;
import br.com.virtualstore.repository.impl.PedidoRepositoryMemoria;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private int proximoId = 1;

    public PedidoService() {
        this.pedidoRepository = new PedidoRepositoryMemoria();
    }

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Carrinho carrinho) {

        if (carrinho == null || carrinho.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "Não é possível criar um pedido com carrinho vazio."
            );
        }

        /*
         * Criamos uma nova lista para congelar os dados
         * dos produtos no momento da criação do pedido.
         */
        List<ItemPedido> itensPedido = new ArrayList<>();

        for (ItemCarrinho item : carrinho.getItens()) {

            ItemPedido itemPedido = new ItemPedido(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco(),
                    item.getQuantidade()
            );

            itensPedido.add(itemPedido);
        }

        Pedido pedido = new Pedido(
                proximoId++,
                carrinho.getCliente(),
                itensPedido
        );

        pedidoRepository.salvar(pedido);

        return pedido;
    }

    public Pedido buscarPorId(int id) {
        return pedidoRepository.buscarPorId(id);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.listarTodos();
    }

    public List<Pedido> listarPedidosPorCliente(int clienteId) {
        return pedidoRepository.buscarPorCliente(clienteId);
    }


    /*
     * Atualiza o status de um pedido existente.
     *
     * Primeiro buscamos o pedido pelo ID.
     * Se ele não existir, retornamos false.
     * Caso exista, atualizamos seu status.
     */
    public boolean atualizarStatus(int id, StatusPedido novoStatus) {

        Pedido pedido = pedidoRepository.buscarPorId(id);

        if (pedido == null) {
            return false;
        }

        pedido.setStatus(novoStatus);

        return true;
    }


    /*
     * Lista todos os pedidos realizados por um cliente.
     * Mantemos o nome antigo para preservar a compatibilidade
     * com as classes que já utilizam esse método.
     */
    public List<Pedido> listarPedidosCliente(int clienteId) {
        return pedidoRepository.buscarPorCliente(clienteId);
    }


}
