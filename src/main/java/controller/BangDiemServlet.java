package controller;

import dao.DiemCaNhanDAO;
import model.DiemCaNhan;
import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class BangDiemServlet extends HttpServlet {

    DiemCaNhanDAO diemDAO = new DiemCaNhanDAO();

    
    private SinhVien getSinhVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        SinhVien sv = null;

        if (session != null) {
            sv = (SinhVien) session.getAttribute("sinhVien");
        }

        if (sv == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }

        return sv;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        SinhVien sv = getSinhVien(request, response);
        if (sv == null) return;

        String maSV = sv.getMaSV();
        String hocKy = request.getParameter("hocKy");

        // Danh sách học kỳ của sinh viên
        List<String> dsHocKy = diemDAO.getHocKyByMaSV(maSV);

        List<DiemCaNhan> dsDiem;
        double gpa;

        if (hocKy != null && !hocKy.isEmpty()) {
            dsDiem = diemDAO.findByMaSVAndHocKy(maSV, hocKy);
        } else {
            dsDiem = diemDAO.findByMaSV(maSV);
            hocKy = "";
        }

        gpa = diemDAO.tinhGPA(dsDiem);

        request.setAttribute("dsDiem", dsDiem);
        request.setAttribute("gpa", gpa);
        request.setAttribute("dsHocKy", dsHocKy);
        request.setAttribute("selectedHocKy", hocKy);

        request.getRequestDispatcher("/jsp/bangdiem.jsp")
               .forward(request, response);
    }
}
