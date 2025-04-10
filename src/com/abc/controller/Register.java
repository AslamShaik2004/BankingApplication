package com.abc.controller;

import com.abc.model.Model;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String custid = request.getParameter("custid");
        String saccno = request.getParameter("accno");
        int accno = Integer.parseInt(saccno);
        String pwd = request.getParameter("pwd");
        String sbal = request.getParameter("bal");
        int bal = Integer.parseInt(sbal);
        String email = request.getParameter("email");

        try {
            Model m = new Model();
            m.setName(name);
            m.setCustid(custid);
            m.setAccno(accno);
            m.setPwd(pwd);
            m.setBal(bal);
            m.setEmail(email);

            boolean exists = m.checkDuplicateUser();  // NEW METHOD
            if (exists) {
                response.sendRedirect("/BankingApplication/UserExists.html");
            } else {
                boolean success = m.register();
                if (success) {
                    response.sendRedirect("/BankingApplication/SuccessReg.html");
                } else {
                    response.sendRedirect("/BankingApplication/FailureReg.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
