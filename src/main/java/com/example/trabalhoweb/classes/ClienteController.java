package com.example.trabalhoweb.classes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ClienteController {
    ProdutoRepositorio repositorio;

    ClienteController(ProdutoRepositorio produtoRepositorio){
        this.repositorio = produtoRepositorio;
    }
    @RequestMapping("/listaprodutos")
    public void axios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Produto> arrayList = (ArrayList<Produto>) repositorio.findAll();
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println("<table class=\"table\">");
        response.getWriter().println("<thead>");


        response.getWriter().println("<tr>");
        response.getWriter().println("<th scope=\"col\">Nome</th>");
        response.getWriter().println("<th scope=\"col\">Preço</th>");
        response.getWriter().println("<th scope=\"col\">Estoque</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");

        for(Produto p : arrayList){
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>"+p.getNome());
            response.getWriter().println("<td>"+p.getPreco());
            response.getWriter().println("<td>"+p.getEstoque());
            response.getWriter().println("<a href=\"addcarrinho?id=\"" + p.getId()+">");
            response.getWriter().println("<td>"+p.getId() + "</td>");
            response.getWriter().println("</a>");
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");

        response.getWriter().println("</table>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
    @RequestMapping("/addcarrinho")
    public void doCarrinho(HttpServletRequest request, HttpServletResponse response){
        var id = request.getParameter("id");
        var produtoEscolhido = repositorio.findById(Integer.valueOf(id));
        carrinho.save(produtoEscolhido);

    }
}
