package org.example.view;

import org.example.DAO.ProdutoDAO;
import org.example.DAO.ProdutoDAOImpl;
import org.example.DAO.UsuarioDAO;
import org.example.DAO.UsuarioDAOImpl;
import org.example.model.*;

import java.util.List;
import java.util.Scanner;

public class TelaAdministrativa {

    private ProdutoDAO produtoDAO;
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public TelaAdministrativa(Scanner scanner) {
        this.scanner = scanner;
        this.produtoDAO = new ProdutoDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 8) {
            System.out.println("\nMENU ADMINISTRATIVO");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Buscar produto");
            System.out.println("3. Remover produto");
            System.out.println("4. Atualizar produto");
            System.out.println("5. Adicionar produto no carrinho do usuário");
            System.out.println("6. Remover produto do carrinho do usuário");
            System.out.println("7. Atualizar quantidade no carrinho do usuário");
            System.out.println("8. Sair");
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
                    adicionarProdutoCarrinhoUsuario();
                    break;
                case 6:
                    removerProdutoCarrinhoUsuario();
                    break;
                case 7:
                    atualizarQuantidadeCarrinhoUsuario();
                    break;
                case 8:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void cadastrarProduto() {
        System.out.println("\nCADASTRAR PRODUTO");

        System.out.print("Nome do Produto: ");
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
        System.out.println("Produto cadastrado com sucesso");
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

    public void removerProduto() {
        System.out.println("\nREMOVER PRODUTO");
        System.out.print("Digite o ID do produto que deseja remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto Não Encontrado");
            return;
        }

        produtoDAO.remover(id);
        System.out.println("Produto removido com sucesso");
    }

    public void atualizarProduto() {
        System.out.println("\nATUALIZAR PRODUTO");
        System.out.print("Digite o ID do produto a ser atualizado: ");
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
        System.out.println("Produto atualizado com sucesso");
    }

    private Carrinho buscarCarrinhoUsuario() {
        System.out.print("Digite o login do usuário: ");
        String login = scanner.nextLine();

        Usuario usuario = usuarioDAO.buscarPorLogin(login);
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return null;
        }

        System.out.println("Usuário encontrado: " + usuario.getNome());
        return CarrinhoMemoria.getCarrinho(usuario);
    }

    public void adicionarProdutoCarrinhoUsuario() {
        System.out.println("\nADICIONAR PRODUTO NO CARRINHO DO USUÁRIO");

        Carrinho carrinho = buscarCarrinhoUsuario();
        if (carrinho == null) return;

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
        System.out.println("Produto adicionado ao carrinho do usuário com sucesso!");
    }

    public void removerProdutoCarrinhoUsuario() {
        System.out.println("\nREMOVER PRODUTO DO CARRINHO DO USUÁRIO");

        Carrinho carrinho = buscarCarrinhoUsuario();
        if (carrinho == null) return;

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho do usuário está vazio!");
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
        System.out.println("Produto removido do carrinho do usuário com sucesso!");
    }

    public void atualizarQuantidadeCarrinhoUsuario() {
        System.out.println("\nATUALIZAR QUANTIDADE NO CARRINHO DO USUÁRIO");

        Carrinho carrinho = buscarCarrinhoUsuario();
        if (carrinho == null) return;

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho do usuário está vazio!");
            return;
        }

        System.out.println("Itens no carrinho:");
        carrinho.getItens().forEach(item -> System.out.println(
                "ID: " + item.getProduto().getId() +
                        " | Nome: " + item.getProduto().getNome() +
                        " | Quantidade: " + item.getQuantidade()
        ));

        System.out.print("Digite o ID do produto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ItemCarrinho itemEncontrado = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId() == id)
                .findFirst()
                .orElse(null);

        if (itemEncontrado == null) {
            System.out.println("Produto não encontrado no carrinho!");
            return;
        }

        System.out.print("Nova quantidade (atual: " + itemEncontrado.getQuantidade() + "): ");
        int novaQuantidade = scanner.nextInt();
        scanner.nextLine();

        if (novaQuantidade <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        if (novaQuantidade > itemEncontrado.getProduto().getEstoque()) {
            System.out.println("Quantidade indisponível! Estoque: " + itemEncontrado.getProduto().getEstoque());
            return;
        }

        itemEncontrado.setQuantidade(novaQuantidade);
        System.out.println("Quantidade atualizada com sucesso!");
    }







}
