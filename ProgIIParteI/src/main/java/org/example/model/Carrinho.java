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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemCarrinho itemCarrinho){
        //add metodo proprio de lista
        itens.add(itemCarrinho);
    }

    public void removerItem(Produto produto){
        //iterando na coleção itens, se o produto Id de getProduto for igual ao produto ID passado no parametro ele remove
        itens.removeIf(item -> item.getProduto().getId() == produto.getId());
    }

    public void limpar(){
        //metodo proprio da lista
        itens.clear();
    }

}
