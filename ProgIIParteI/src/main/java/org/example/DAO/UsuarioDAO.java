package org.example.DAO;

import org.example.model.Usuario;

public interface UsuarioDAO {

    void cadastrar(Usuario usuario);
    Usuario buscarPorLogin(String login);
    boolean loginExiste(String login);
    boolean validarSenha(String login, String senha);
}
