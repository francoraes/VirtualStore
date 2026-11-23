
package br.com.virtualstore.repository;

import br.com.virtualstore.model.Pedido;
import java.util.List;

public interface PedidoRepository {

    void salvar(Pedido pedido);

    Pedido buscarPorId(int id);

    List<Pedido> listarTodos();

    List<Pedido> buscarPorCliente(int clienteId);
}
