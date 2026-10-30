package controller;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private SinhVienDAO sinhVienDAO = new SinhVienDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String maSV = request.getParameter("maSV");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        if (sinhVienDAO.maSVExists(maSV)) {
            request.setAttribute("error", "Mã sinh viên đã tồn tại!");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        if (sinhVienDAO.emailExists(email)) {
            request.setAttribute("error", "Email đã được sử dụng!");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        SinhVien sv = new SinhVien();
        sv.setMaSV(maSV);
        sv.setHoTen(hoTen);
        sv.setEmail(email);
        sv.setMatKhau(pass);

        if (sinhVienDAO.register(sv)) {
            request.setAttribute("message", "Đăng ký thành công, mời đăng nhập!");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký thất bại!");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }
}
