package br.com.veloweb.flyweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.veloweb.flyweb.acao.Acao;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/index")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void servlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String paramAcao = request.getParameter("acao");
    	String nomeDaClasse = "br.com.veloweb.flyweb.acao"+paramAcao;
    	String nome;
    	
    	try {
    		Class classe = Class.forName(nomeDaClasse);
    		Acao acao = (Acao) classe.newInstance();
    		nome = acao.executa(request, response);
    	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}
    	
    	String[] tipoEEndereco = nome.split(":");
    	if(tipoEEndereco[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/"+tipoEEndereco[1]);
			rd.forward(request, response);
		} else {
			response.sendRedirect("entrada?acao="+tipoEEndereco[1]);
		}
    }

}
