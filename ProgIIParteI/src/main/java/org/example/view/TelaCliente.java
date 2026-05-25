package org.example.view;

import org.example.DAO.ProdutoDAO;
import org.example.DAO.ProdutoDAOImpl;
import org.example.model.Carrinho;
import org.example.model.ItemCarrinho;
import org.example.model.Produto;
import org.example.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class TelaCliente {

    private Usuario usuario;
    private Carrinho carrinho;
    private ProdutoDAO produtoDAO;
    private Scanner scanner;

    public TelaCliente(Scanner scanner, Usuario usuario) {
        this.scanner = scanner;
        this.usuario = usuario;
        this.carrinho = new Carrinho(usuario);
        this.produtoDAO = new ProdutoDAOImpl();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 6) {
            System.out.println("\nTELA DO CLIENTE");
            System.out.println("Bem vindo, " + usuario.getNome() + "!");
            System.out.println("1. Buscar produto");
            System.out.println("2. Adicionar ao carrinho");
            System.out.println("3. Retirar do carrinho");
            System.out.println("4. Confirmar compra");
            System.out.println("5. Ver Carrinho");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarProduto();
                    break;
                case 2:
                    adicionarAoCarrinho();
                    break;
                case 3:
                    removerDoCarrinho();
                    break;
                case 4:
                    confirmarCompra();
                    break;
                case 5:
                    verCarrinho();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void buscarProduto() {
        System.out.println("\nBUSCAR PRODUTO");
        System.out.println("1. Buscar por nome");
        System.out.println("2. Buscar por ID");
        System.out.print("Escolha uma opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Digite o nome do produto: ");
                String nome = scanner.nextLine();
                List<Produto> produtos = produtoDAO.buscarPorNome(nome);
                if (produtos.isEmpty()) {
                    System.out.println("Produto Não Encontrado");
                } else {
                    produtos.forEach(p -> System.out.println(
                            "ID: " + p.getId() +
                                    " | Nome: " + p.getNome() +
                                    " | Descrição: " + p.getDescricao() +
                                    " | Preço: R$" + p.getPreco() +
                                    " | Estoque: " + p.getEstoque()
                    ));
                }
                break;
            case 2:
                System.out.print("Digite o ID do produto: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                Produto produto = produtoDAO.buscarPorId(id);
                if (produto == null) {
                    System.out.println("Produto Não Encontrado");
                } else {
                    System.out.println(
                            "ID: " + produto.getId() +
                                    " | Nome: " + produto.getNome() +
                                    " | Descrição: " + produto.getDescricao() +
                                    " | Preço: R$" + produto.getPreco() +
                                    " | Estoque: " + produto.getEstoque()
                    );
                }
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public void adicionarAoCarrinho() {
        System.out.println("\nADICIONAR AO CARRINHO");
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto Não Encontrado");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        if (quantidade > produto.getEstoque()) {
            System.out.println("Quantidade indisponível! Estoque: " + produto.getEstoque());
            return;
        }

        carrinho.adicionarItem(new ItemCarrinho(produto, quantidade));
        System.out.println("Produto adicionado ao carrinho!");
    }

    public void removerDoCarrinho() {
        System.out.println("\nRETIRAR DO CARRINHO");

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        System.out.println("Itens no carrinho:");
        carrinho.getItens().forEach(item -> System.out.println(
                "ID: " + item.getProduto().getId() +
                        " | Nome: " + item.getProduto().getNome() +
                        " | Quantidade: " + item.getQuantidade()
        ));

        System.out.print("Digite o ID do produto a retirar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto Não Encontrado");
            return;
        }

        carrinho.removerItem(produto);
        System.out.println("Produto retirado do carrinho!");
    }

    public void verCarrinho(){
        System.out.println("Itens da sua compra:");
        double total = 0;
        for (ItemCarrinho item : carrinho.getItens()) {
            double subtotal = item.getProduto().getPreco() * item.getQuantidade();
            total += subtotal;
            System.out.println(
                    "- " + item.getProduto().getNome() +
                            " x" + item.getQuantidade() +
                            " = R$" + subtotal
            );
        }
    }

    public void confirmarCompra() {
        System.out.println("\nCONFIRMAR COMPRA");

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        System.out.println("Itens da compra:");
        double total = 0;
        for (ItemCarrinho item : carrinho.getItens()) {
            double subtotal = item.getProduto().getPreco() * item.getQuantidade();
            total += subtotal;
            System.out.println(
                    "- " + item.getProduto().getNome() +
                            " x" + item.getQuantidade() +
                            " = R$" + subtotal
            );
        }

        System.out.println("Total: R$" + total);
        System.out.print("Confirmar compra? Digite s ou n : ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            carrinho.limpar();
            System.out.println("Compra confirmada! Obrigado, " + usuario.getNome() + "!");
        } else {
            System.out.println("Compra cancelada.");
        }
    }
}
