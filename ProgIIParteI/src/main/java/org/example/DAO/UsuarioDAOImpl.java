package org.example.DAO;

import org.example.conexao.ConnectionFactory;
import org.example.model.Perfil;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void cadastrar(Usuario usuario){
        String sql = "INSERT INTO usuarios (nome,sobrenome,login, senha, perfil) VALUES (?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getPerfil().name());

        } catch (Exception e){
            System.out.println("Erro ao cadastrar usuário " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorLogin(String login){
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Usuario usuario = new Usuario(
                  rs.getInt("id"),
                  rs.getString("nome"),
                  rs.getString("sobrenome"),
                  rs.getString("login"),
                  rs.getString("senha"),
                        Perfil.valueOf(rs.getString("perfil"))
                );
                return usuario;
            }
        } catch (Exception e){
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean loginExiste(String login){
        String sql = "SELECT id FROM usuarios WHERE login = ?";
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e){
            System.out.println("Erro ao verificar login: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean validarSenha(String login, String senha){
        String sql = "SELECT id FROM usuarios WHERE login = ? AND senha = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e){
            System.out.println("Erro ao validar senha " + e.getMessage());
        }
        return false;
    }
}
