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

    public List<Aluno> pesquisar(Aluno aluno) {
        List<Aluno> alunos = new ArrayList();
        try {
            String sql = "SELECT * FROM alunos WHERE ra=? ORDER BY nome ASC";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, aluno.getRa());

            System.out.println("RA:" + aluno.getRa());

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
            throw new RuntimeException("Falha ao listar.", ex);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("RA inválido", nfe);
        }
    }
}
