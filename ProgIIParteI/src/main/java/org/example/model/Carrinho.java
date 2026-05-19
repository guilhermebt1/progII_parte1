package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private Usuario usuario;
    private List<ItemCarrinho> itens;

    public Carrinho(Usuario usuario) {
        this.usuario = usuario;
        //Iniciando a lista vazia pois o carrinho começa vazio
        this.itens = new ArrayList<>();
    }

}
