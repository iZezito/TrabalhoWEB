package com.example.trabalhoweb;

import com.example.trabalhoweb.classes.Produto;
import com.example.trabalhoweb.classes.ProdutoRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ProdutoController {
    ProdutoRepositorio produtos;
    ProdutoController(ProdutoRepositorio produtoRepositorio){
        this.produtos = produtoRepositorio;
    }

    @RequestMapping("/cadastroproduto")
    public void axios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var nome = request.getParameter("nome");
        var preco = Float.parseFloat(request.getParameter("preco"));
        var descricao = request.getParameter("descricao");
        var estoque = Integer.parseInt( request.getParameter("estoque"));

        Produto p = new Produto(preco, nome, descricao, estoque);
        produtos.save(p);

    }


    @RequestMapping("/lojista")
    public void lojista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Produto> arrayList  = (ArrayList<Produto>) produtos.findAll();

        response.getWriter().println("<html>");
        response.getWriter().println("<table>");
        response.getWriter().println("<thead>");


        response.getWriter().println("<tr>");
        response.getWriter().println("<th>Nome</th>");
        response.getWriter().println("<th>Preço</th>");
        response.getWriter().println("<th>Estoque</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");

        for(Produto p : arrayList){
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>"+p.getNome());
            response.getWriter().println("<td>"+p.getPreco());
            response.getWriter().println("<td>"+p.getEstoque());
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");

        response.getWriter().println("</table>");



        response.getWriter().println("</html>");

    }


}
