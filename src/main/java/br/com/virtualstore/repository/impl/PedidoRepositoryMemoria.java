
package br.com.virtualstore.repository.impl;

import br.com.virtualstore.model.Pedido;
import br.com.virtualstore.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryMemoria implements PedidoRepository {

    private final List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public Pedido buscarPorId(int id) {

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public List<Pedido> buscarPorCliente(int clienteId) {

        List<Pedido> encontrados = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (pedido.getCliente().getId() == clienteId) {
                encontrados.add(pedido);
            }
        }

        return encontrados;
    }
}
