package models;

import beans.Aluno;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

public class AlunosModel implements Serializable {

    // declarando as variáveis globais para serem
    // utilizadas ao longo do programa (classe)
    private Connection conexao = null;
    private String status;

    // método construtor
    public AlunosModel() throws SQLException {
        this.conexao = DBConnection.getInstance().getConnection();
    }

    // método listar
    public List<Aluno> listar() {
        // variável para receber a lista de alunos
        // que virão do banco de dados
        List<Aluno> alunos = new ArrayList();

        // tratamento de erros
        try {
            String sql = "SELECT * FROM alunos ORDER BY nome ASC";
            try (
                    // preparando a consulta
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    // declarar uma variável para receber o resultado
                    // e executa a instrução no banco
                    ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setRa(rs.getInt("ra"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setCurso(rs.getString("curso"));

                    // colocar o objeto aluno com seus dados na lista
                    // de objetos (alunos)
                    alunos.add(aluno);
                }
                rs.close(); // fecha o ResultSet
                ps.close(); // fecha a conexão com o banco de dados
            }

            return alunos;
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao listar.", ex);
        }
    }

    public List<Aluno> pesquisar(Aluno aluno, String tipo) {
        List<Aluno> alunos = new ArrayList();
        PreparedStatement ps = null;
        String sql = new String();
        try {
            switch (tipo) {
                case "ra":
                    sql = "SELECT * FROM alunos WHERE ra = ? ORDER BY nome ASC";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, aluno.getRa());
                    break;

                case "nome":
                    sql = "SELECT * FROM alunos WHERE nome = ? ORDER BY nome ASC";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, aluno.getNome());
                    break;

                case "curso":
                    sql = "SELECT * FROM alunos WHERE curso = ? ORDER BY nome ASC";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, aluno.getCurso());
                    break;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setRa(rs.getInt("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));
                alunos.add(aluno);
            }
            rs.close();
            ps.close();
            return alunos;
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao Pesquisar.", ex);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("RA inválido", nfe);
        }
    }

    // método para inserir um aluno
    public void inserir(Aluno aluno) {
        try {
            String sql
                    = "INSERT INTO alunos (ra, nome, curso) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                // atribuir os valor do objeto ao "ps"
                ps.setInt(1, aluno.getRa());
                ps.setString(2, aluno.getNome());
                ps.setString(3, aluno.getCurso());

                // executar o SQL no banco de dados
                ps.execute();
                ps.close();
            }
            conexao.close();
            this.status
                    = "Aluno [" + aluno.getNome() + "] inserido com sucesso!";
        } catch (SQLException ex) {
            this.status = "Erro ao inserir o aluno [" + ex.getMessage() + "]";
        }
    }

    public void atualizar(Aluno aluno) {
        try {
            String sql = "UPDATE alunos SET nome = ?, curso = ? WHERE ra = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, aluno.getNome());
                ps.setString(2, aluno.getCurso());
                ps.setInt(3, aluno.getRa());

                ps.executeUpdate();
                ps.close();
            }
            conexao.close();
            this.status
                    = "Aluno [" + aluno.getRa() + "] alterado com sucesso!";
        } catch (SQLException ex) {
            this.status = "Erro ao alterar o aluno [" + ex.getMessage() + "]";
        }
    }

    public void excluir(Aluno aluno) {
        String nome = aluno.getNome();
        try {
            String sql = "DELETE FROM alunos WHERE ra = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, aluno.getRa());

                ps.execute();
                ps.close();
            }
            conexao.close();
            this.status
                    = "Aluno [" + nome + "] excluído com sucesso!";
        } catch (SQLException ex) {
            this.status = "Erro ao excluir o aluno [" + ex.getMessage() + "]";
        }
    }

    @Override
    public String toString() {
        return status;
    }
}
