package org.example.DAO;

import org.example.model.Produto;

import java.util.List;

public interface ProdutoDAO {

    //void cadastrar(Produto produto);

    Produto buscarPorId(int id);

    List<Produto> buscarPorNome(String produtoNome);

    //void remover(int id);

    //void atualizar(Produto produto);
}
