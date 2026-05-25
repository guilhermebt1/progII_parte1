package org.example.view;

import org.example.DAO.UsuarioDAO;
import org.example.DAO.UsuarioDAOImpl;
import org.example.model.Perfil;
import org.example.model.Usuario;

import java.util.Scanner;

public class MenuPrincipal {

    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public MenuPrincipal(Scanner scanner) {
        this.scanner = scanner;
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Login - Usuário");
            System.out.println("3. Login - Administrativo");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    new MenuCadastroUsuario(scanner).exibir();
                    break;
                case 2:
                    efetuarLogin(Perfil.USER);
                    break;
                case 3:
                    efetuarLogin(Perfil.ADMIN);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void efetuarLogin(Perfil perfilEsperado) {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioDAO.buscarPorLogin(login);

        if (usuario != null && usuarioDAO.validarSenha(login, senha)) {
            if (usuario.getPerfil() == perfilEsperado) {
                if (perfilEsperado == Perfil.ADMIN) {
                    new TelaAdministrativa(scanner).exibir();
                } else {
                    new TelaCliente(scanner, usuario).exibir();
                }
            } else {
                System.out.println("Acesso negado pois o perfil está incorreto.");
            }
        } else {
            System.out.println("Login ou senha inválidos");
        }
    }
}
