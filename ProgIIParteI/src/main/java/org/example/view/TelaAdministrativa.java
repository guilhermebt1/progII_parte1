package org.example.view;

import org.example.DAO.ProdutoDAO;
import org.example.DAO.ProdutoDAOImpl;
import org.example.model.Produto;
import java.util.List;
import java.util.Scanner;

public class TelaAdministrativa {

    private ProdutoDAO produtoDAO;
    private Scanner scanner;

    public TelaAdministrativa(Scanner scanner) {
        this.scanner = scanner;
        this.produtoDAO = new ProdutoDAOImpl();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 5) {
            System.out.println("\n=== TELA ADMINISTRATIVA ===");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Buscar produto");
            System.out.println("3. Remover produto");
            System.out.println("4. Atualizar produto");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    buscarProduto();
                    break;
                case 3:
                    removerProduto();
                    break;
                case 4:
                    atualizarProduto();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastrarProduto() {
        System.out.println("\n=== CADASTRAR PRODUTO ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();

        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Produto produto = new Produto(0, nome, descricao, preco, estoque);
        produtoDAO.cadastrar(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void buscarProduto() {
        System.out.println("\n=== BUSCAR PRODUTO ===");
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

    public void removerProduto() {
        System.out.println("\n=== REMOVER PRODUTO ===");
        System.out.print("Digite o ID do produto a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto Não Encontrado");
            return;
        }

        produtoDAO.remover(id);
        System.out.println("Produto removido com sucesso!");
    }

    public void atualizarProduto() {
        System.out.println("\n=== ATUALIZAR PRODUTO ===");
        System.out.print("Digite o ID do produto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto Não Encontrado");
            return;
        }

        System.out.println("Produto encontrado: " + produto.getNome());
        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Novo nome (" + produto.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) produto.setNome(nome);

        System.out.print("Nova descrição (" + produto.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        if (!descricao.isBlank()) produto.setDescricao(descricao);

        System.out.print("Novo preço (" + produto.getPreco() + "): ");
        String precoStr = scanner.nextLine();
        if (!precoStr.isBlank()) produto.setPreco(Double.parseDouble(precoStr));

        System.out.print("Novo estoque (" + produto.getEstoque() + "): ");
        String estoqueStr = scanner.nextLine();
        if (!estoqueStr.isBlank()) produto.setEstoque(Integer.parseInt(estoqueStr));

        produtoDAO.atualizar(produto);
        System.out.println("Produto atualizado com sucesso!");
    }
}
