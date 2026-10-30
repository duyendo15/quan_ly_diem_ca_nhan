package controller;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private SinhVienDAO sinhVienDAO = new SinhVienDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        
        String pass = request.getParameter("password");

        SinhVien sv = sinhVienDAO.login(email, pass);
        if (sv != null) {
            HttpSession session = request.getSession(true);
            
            session.setAttribute("sinhVien", sv);
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}
