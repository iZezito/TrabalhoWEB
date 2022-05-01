package com.example.trabalhoweb.classes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ClienteController {
    ProdutoRepositorio repositorio;
    Carrinho carrinho = new Carrinho(new ArrayList<Produto>());



    ClienteController(ProdutoRepositorio repositorio2){
        this.repositorio = repositorio2;
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
        response.getWriter().println("<th scope=\"col\">Id</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");

        for(Produto p : arrayList){

            response.getWriter().println("<tr>");
            response.getWriter().println("<td>"+p.getNome()+"</td>");
            response.getWriter().println("<td>"+p.getPreco()+"</td>");
            response.getWriter().println("<td>"+p.getEstoque()+"</td>");
            //setar parametro e enviar
            if(p.getEstoque() == 0){
                response.getWriter().println("<td>Sem estoque</td>");
            }else{
                response.getWriter().println("<td><a class=\"nav-link\"href=\"addcarrinho?id="+p.getId() + "&comand=add"+"\">Adicionar</a></td>");

            }
            response.getWriter().println();
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");
        response.getWriter().println("</table>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
    @RequestMapping("/addcarrinho")
    public void doCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<Produto> arrayList = (ArrayList<Produto>) repositorio.findAll();
        var comand = request.getParameter("comand");


        Cookie[] cookies = request.getCookies();
        if(cookies[0]==null){
            Cookie cookie = new Cookie("carrinho", "");
            cookie.setMaxAge(60*60*48);
            response.addCookie(cookie);
        }

        if(comand.equals("add")){
            for(Produto p: arrayList){
                if(p.getId() == id) {
                    carrinho.addProduto(p);
                    p.estoque--;
                    repositorio.flush();
                }
            }

        }else if(comand.equals("remove")){
            for(Produto p: arrayList){
                if(p.getId() == id) {
                    carrinho.removeProduto(id);
                    p.estoque++;
                    repositorio.flush();
                }
            }

        }
        cookies[0].setValue(cookies[0].getValue() + "/"+ id);




        response.addCookie(cookies[0]);
        response.getWriter().println(cookies[0].getValue());



    }
    @RequestMapping("/carrinho")
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        ArrayList<Produto> produtos = carrinho.getProdutos();
        String idP = "";
        int qtd;
        int inicio = 1;
        int fim = 2;
        for(Produto p : produtos){
            idP = cookies[0].getValue().substring(inicio, fim);
            inicio = fim + 1;
            fim += 1;

            ///5/5/
            //1, 2; 2, 3;



        }
        ArrayList<Produto> arrayList = carrinho.getProdutos();
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
        response.getWriter().println("<th scope=\"col\">Id</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");

        for(Produto p : arrayList){

            response.getWriter().println("<tr>");

            response.getWriter().println("<td>"+p.getNome()+"</td>");
            response.getWriter().println("<td>"+p.getPreco()+"</td>");
            response.getWriter().println("<td>"+idP+"</td>");

            response.getWriter().println("<td><a class=\"nav-link\"href=\"addcarrinho?id="+p.getId() + "&comand=remove"+"\">Remover</a></td>");
            //setar parametro e enviar
            response.getWriter().println();
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");
        response.getWriter().println("</table>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");

    }
}
