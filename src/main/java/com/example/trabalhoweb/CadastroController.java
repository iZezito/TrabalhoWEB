package com.example.trabalhoweb;

import com.example.trabalhoweb.classes.Aaa;
import com.example.trabalhoweb.classes.Cadastro;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@Controller
public class CadastroController {
    Aaa banco;
    CadastroController(Aaa contactRepository) {
        this.banco = contactRepository;
    }







    ArrayList<Object> arrayList = new ArrayList<>();


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        ArrayList<Cadastro> cadastros = (ArrayList<Cadastro>) banco.findAll();


        var email = request.getParameter("email");
        session.setAttribute("email", email);
        var emails = session.getAttribute("email");
        var senha = request.getParameter("senha");



        for(Cadastro c : cadastros){
            if(email.equals(c.getEmail()) && senha.equals(c.getSenha()) && c.isFuncao()){
                RequestDispatcher encaminhar = request.getRequestDispatcher("/lojista");
                encaminhar.forward(request, response);
                break;


            }else if(email.equals(c.getEmail()) && senha.equals(c.getSenha())){

                    request.setAttribute("objeto", c);
                    RequestDispatcher encaminhar = request.getRequestDispatcher("/cliente");
                    encaminhar.forward(request, response);
                    break;
            }else {

                response.getWriter().println("Credenciais inválidas");
                response.getWriter().println(c.getEmail());
                response.getWriter().println(c.getSenha());
            }
        }




    }
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public void cadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {



        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        boolean funcao = Boolean.parseBoolean(request.getParameter("funcao"));

        Cadastro cadastro = new Cadastro(nome, email, senha, funcao);
        banco.save(cadastro);
        //cadastros.addAll(banco.findAll());

        response.getWriter().println("bbbb");

    }


}