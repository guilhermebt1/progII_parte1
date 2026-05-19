package org.example.view;

import org.example.DAO.UsuarioDAO;
import org.example.DAO.UsuarioDAOImpl;
import org.example.model.Perfil;
import org.example.model.Usuario;

import java.util.Scanner;

public class MenuCadastroUsuario {

    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public MenuCadastroUsuario(Scanner scanner) {
        this.scanner = scanner;
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public void exibir() {
        System.out.println("\nCADASTRO DE USUÁRIO");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Confirme a senha: ");
        String confirmaSenha = scanner.nextLine();

        if (!validarCampos(nome, sobrenome, login, senha)) {
            System.out.println("Erro: campo vazio!");
            return;
        }

        if (!validarSenhas(senha, confirmaSenha)) {
            System.out.println("Erro: senhas não são iguais");
            return;
        }

        if (usuarioDAO.loginExiste(login)) {
            System.out.println("Erro: usuário já existe!");
            return;
        }

        Usuario usuario = new Usuario(0, nome, sobrenome, login, senha, Perfil.USER);
        usuarioDAO.cadastrar(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private boolean validarSenhas(String senha, String confirmaSenha) {
        return senha.equals(confirmaSenha);
    }

    private boolean validarCampos(String nome, String sobrenome, String login, String senha) {
        return !nome.isBlank() && !sobrenome.isBlank() && !login.isBlank() && !senha.isBlank();
    }

}
