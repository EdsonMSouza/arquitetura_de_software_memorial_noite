<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- a linha abaixo faz a inclusão de um outro arquivo no arquivo atual --%>
<c:import url="topo.jsp" />

<div class="alert-success text-center espaco">
    Listagem de Usuários
</div>

<c:forEach var="aluno" items="${listaAlunos}">
    <p>ID: ${aluno.id}</p>
    <p>RA: ${aluno.ra}</p>
    <p>NOME: ${aluno.nome}</p>
    <p>CURSO: ${aluno.curso}</p>
    <br>
</c:forEach>


<c:import url="rodape.jsp" />