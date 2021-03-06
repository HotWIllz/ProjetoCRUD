/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import Objetos.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class UsuariosDAO {

    public List<Usuarios> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuarios> usuarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_usuarios");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                usuarios.add(u);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao obter dados " + e);

        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return usuarios;
    }

    public void create(Usuarios u) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbl_usuarios (nome, login, senha, tipo) VALUES (?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getLogin());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTipo());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Cadstrado com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro" + e);
        } finally {
            Conexao.closeConnection(con, stmt);

        }

    }

    public void update(Usuarios u) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbl_usuarios SET nome = ?, login = ?, senha = ?, tipo = ? WHERE id = ?");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getLogin());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTipo());
            stmt.setInt(5, u.getId());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (Exception e) {
        } finally {
            Conexao.closeConnection(con, stmt);

        }

    }

    public void delete(Usuarios u) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tbl_usuarios WHERE id = ?");
            stmt.setInt(1, u.getId());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Removido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao remover " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public boolean checaLogin(String login, String senha) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean logado = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_usuarios WHERE login = ? and senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                logado = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Usu??rio n??o encontrado");
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return logado;

    }

    public Usuarios dadosUsuario(String login, String senha) {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuarios u = new Usuarios();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_usuarios WHERE login = ? AND senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            while (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao obter dados " + e);

        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return u;
    }

}
