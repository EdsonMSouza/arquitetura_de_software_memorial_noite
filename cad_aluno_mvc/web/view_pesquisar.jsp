<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- a linha abaixo faz a inclusão de um outro arquivo no arquivo atual --%>
<c:import url="topo.jsp" />

<div class="alert-success text-center espaco">
    Pesquisar Usuário
</div>

<form name="pesquisar" method="post" action="AlunosController">
    <div class="row">
        <div class="col-md-5 mb-3">
            <label>RA</label>
            <input type="text" class="form-control" name="ra" />
        </div>
    </div>

    <div class="row">
        <div class="col-md-5 mb-3">
            <input type="hidden" name="operacao" value="Pesquisar" />
            <input type="submit" class="form-control btn btn-primary" name="bt_pesquisar" value="Pesquisar"/>
        </div>
    </div>
    <span class="erro"><c:out value = "${mensagem}" /></span>
</form>

<c:import url="rodape.jsp" />