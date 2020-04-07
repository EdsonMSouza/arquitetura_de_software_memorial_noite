/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author souza
 */
public class AlunosController extends HttpServlet {

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

        // cria uma variável para ser enviada para a View (view_mensagem.jsp)
        // parâmetros ("nome_da_variável", "valor da variável")
        request.setAttribute("dados", "Listagem dos alunos");

        // redireciona para a View (view_mensagem.jsp)
        request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);

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

        // pega o valor enviado na variável "operacao"
        String operacao = request.getParameter("operacao");
        switch (operacao) {
            case "Inserir":
                request.setAttribute("mensagem", "Aluno cadastrado com sucesso!");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Pesquisar":
                request.setAttribute("mensagem", "Tela de Pesquisa");
                request.getRequestDispatcher("view_mensagem.jsp").forward(request, response);
                break;
            case "Editar":
                request.setAttribute("mensagem", "Tela de Edição");
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
