package controller;

import dao.MonHocDAO;
import model.MonHoc;
import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class MonHocServlet extends HttpServlet {

    MonHocDAO monHocDAO = new MonHocDAO();

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

        response.setContentType("text/html; charset=UTF-8");

        SinhVien sv = getSinhVien(request, response);
        if (sv == null) return;

        String action = request.getParameter("action");
        String maMH = request.getParameter("maMH");

        // Sửa môn học
        if ("edit".equals(action) && maMH != null) {
            MonHoc m = monHocDAO.findById(maMH, sv.getMaSV());
            request.setAttribute("monHocEdit", m);
        }

        // Xóa môn học
        if ("delete".equals(action) && maMH != null) {
            monHocDAO.delete(maMH, sv.getMaSV());
            response.sendRedirect(request.getContextPath() + "/monhoc");
            return;
        }

        // Lấy danh sách môn học
        List<MonHoc> ds = monHocDAO.findByMaSV(sv.getMaSV());
        request.setAttribute("dsMonHoc", ds);

        request.getRequestDispatcher("/jsp/monhoc.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        SinhVien sv = getSinhVien(request, response);
        if (sv == null) return;

        MonHoc m = new MonHoc();
        m.setMaMH(request.getParameter("maMH"));
        m.setTenMonHoc(request.getParameter("tenMonHoc"));
        m.setSoTinChi(Integer.parseInt(request.getParameter("soTinChi")));
        m.setMaSV(sv.getMaSV()); // gắn cho SV đang đăng nhập

        String mode = request.getParameter("mode");

        if ("update".equals(mode)) {
            monHocDAO.update(m);
        } else {
            monHocDAO.insert(m);
        }

        response.sendRedirect(request.getContextPath() + "/monhoc");
    }
}
