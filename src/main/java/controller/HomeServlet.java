package controller;

import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        SinhVien sv = (session != null) ? (SinhVien) session.getAttribute("sinhVien") : null;

        if (sv == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

     
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
