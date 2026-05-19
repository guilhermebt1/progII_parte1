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
    private ProdutoDAO produtoDAO;
    private Scanner scanner;

    public TelaCliente(Scanner scanner, Usuario usuario) {
        this.scanner = scanner;
        this.usuario = usuario;
        this.produtoDAO = new ProdutoDAOImpl();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 2) {
            System.out.println("\n=== TELA DO CLIENTE ===");
            System.out.println("Bem vindo, " + usuario.getNome() + "!");
            System.out.println("1. Buscar produto");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            System.out.print("NÃO IMPLEMENTADO - Cadastrar novo produto ");
            System.out.print("NÃO IMPLEMENTADO - Remover Produto ");
            System.out.print("NÃO IMPLEMENTADO - Atualizar Produto ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarProduto();
                    break;
                case 2:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    //Metodos
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
                                    " | Preço: R$" + produto.getPreco() +
                                    " | Estoque: " + produto.getEstoque()
                    );
                }
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}
