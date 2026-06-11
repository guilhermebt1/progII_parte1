package org.example.model;

import java.util.HashMap;
import java.util.Map;
public class CarrinhoMemoria {

    private static final Map<String, Carrinho> carrinhos = new HashMap<>();

    public static Carrinho getCarrinho(Usuario usuario) {
        return carrinhos.computeIfAbsent(usuario.getLogin(), k -> new Carrinho(usuario));
    }

}
