/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Aluno;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AlunosModel;

/**
 *
 * @author souza
 */
public class AlunosController extends HttpServlet {

    int ra;
    String nome;
    String curso;
    Aluno aluno = new Aluno();
    List<Aluno> alunosDados; // armazenar todos os alunos recuperados pelo Model

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // vamos usar o doGet para realizar a listagem dos alunos
        response.setContentType("text/html;charset=UTF-8");

        // Chamar o AlunosModel()
        try {
            // chamar o nosso Model
            AlunosModel am = new AlunosModel();

            // atribui os valores retornados à lista de alunos
            alunosDados = am.listar();

            // cria uma variável para ser enviada para a View (view_mensagem.jsp)
            // parâmetros ("nome_da_variável", "valor da variável")
            request.setAttribute(
                    "listaAlunos",
                    alunosDados);

            // redireciona para a View (view_listar.jsp)
            request.getRequestDispatcher("view_listar.jsp").
                    forward(request, response);

        } catch (SQLException ex) {
            request.setAttribute("mensagem", ex);
            request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // pega o valor enviado na variável "operacao" e "ra"
        String operacao = request.getParameter("operacao");

        /*
            // quando receber o RA do formulário, testar se é um número
            try {
                ra = Integer.parseInt(request.getParameter("ra"));
                System.out.println("RA do Controller: " + ra);
            } catch (NumberFormatException nfe) {
                request.setAttribute("mensagem", "Informe um RA válido");
                request.getRequestDispatcher("view_pesquisar.jsp").forward(request, response);
            }
         */
        switch (operacao) {
            case "Inserir":
                try {
                    // recuperar os valores informados
                    aluno.setRa(Integer.parseInt(request.getParameter("ra")));
                    aluno.setNome(request.getParameter("nome"));
                    aluno.setCurso(request.getParameter("curso"));

                    AlunosModel am = new AlunosModel();
                    // Passo o objeto aluno como parâmetro e insere no banco
                    am.inserir(aluno);

                    request.setAttribute("mensagem",
                            am.toString());
                    request.getRequestDispatcher("view_mensagem.jsp").
                            forward(request, response);

                } catch (SQLException ex) {
                    request.setAttribute("mensagem", ex.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").
                            forward(request, response);

                    // Erro com o formato numérico do RA
                } catch (NumberFormatException nfe) {
                    request.setAttribute("mensagem", "Informe um RA válido.");
                    request.getRequestDispatcher("view_cadastrar.jsp").
                            forward(request, response);
                }
                break;

            case "Pesquisar":
                String valorDigitado = request.getParameter("valor");

                try {
                    // criando o objeto Model para o Aluno
                    AlunosModel am = new AlunosModel();
                    //criar um objeto Aluno para passar como parâmetro
                    //aluno.setRa(ra);

                    // verificar quem está chegando (campo a ser pesquisado)
                    switch (request.getParameter("tipo")) {
                        case "ra":
                            aluno.setRa(Integer.parseInt(valorDigitado));
                            break;

                        case "nome":
                            aluno.setNome(valorDigitado);
                            break;

                        case "curso":
                            aluno.setCurso(valorDigitado);
                            break;
                    }

                    // chama o model para realizar a pesquisa
                    alunosDados = am.pesquisar(aluno, request.getParameter("tipo"));

                    // testar se há registros a serem exibidos
                    if (alunosDados.isEmpty()) {
                        request.setAttribute("mensagem", "Registro não encontrado.");
                        request.getRequestDispatcher("view_pesquisar.jsp").
                                forward(request, response);
                    } else {
                        request.setAttribute("listaAlunos", alunosDados);
                        request.getRequestDispatcher("view_listar.jsp").
                                forward(request, response);
                    }
                } catch (SQLException ex) {
                    request.setAttribute("mensagem", ex.getMessage());
                    request.getRequestDispatcher("view_mensagem.jsp").
                            forward(request, response);

                    // Erro com o formato numérico do RA
                } catch (NumberFormatException nfe) {
                    request.setAttribute("mensagem", "Digite um RA válido");
                    request.getRequestDispatcher("view_pesquisar.jsp").
                            forward(request, response);
                }
                break;

            case "Editar":
                request.setAttribute("mensagem", ra);
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Atualizar":
                request.setAttribute("mensagem", "Atualizado com sucesso!!!");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Excluir":
                request.setAttribute("mensagem", "TAluno removido com sucesso!!!");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
