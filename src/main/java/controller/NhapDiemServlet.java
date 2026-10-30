package controller;

import dao.DiemCaNhanDAO;
import dao.MonHocDAO;
import model.DiemCaNhan;
import model.MonHoc;
import model.SinhVien;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class NhapDiemServlet extends HttpServlet {

    MonHocDAO monHocDAO = new MonHocDAO();
    DiemCaNhanDAO diemDAO = new DiemCaNhanDAO();

    // Lấy sinh viên đang đăng nhập
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
        String idStr = request.getParameter("id");

        // Xóa điểm
        if ("delete".equals(action) && idStr != null) {
            int id = Integer.parseInt(idStr);
            diemDAO.deleteByIdAndMaSV(id, sv.getMaSV());
            response.sendRedirect(request.getContextPath() + "/nhapdiem");
            return;
        }

        // Sửa điểm
        if ("edit".equals(action) && idStr != null) {
            int id = Integer.parseInt(idStr);
            DiemCaNhan diemEdit = diemDAO.findByIdAndMaSV(id, sv.getMaSV());
            request.setAttribute("diemEdit", diemEdit);
        }

        // Lấy dữ liệu của SV đang đăng nhập
        List<MonHoc> dsMon = monHocDAO.findByMaSV(sv.getMaSV());
        List<DiemCaNhan> dsDiem = diemDAO.findByMaSV(sv.getMaSV());

        request.setAttribute("dsMon", dsMon);
        request.setAttribute("dsDiem", dsDiem);

        request.getRequestDispatcher("/jsp/nhapdiem.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        SinhVien sv = getSinhVien(request, response);
        if (sv == null) return;

        String diemID = request.getParameter("diemID");

        double dtx = toDouble(request.getParameter("diemTX"));
        double dgk = toDouble(request.getParameter("diemGK"));
        double dck = toDouble(request.getParameter("diemCK"));

        double tongKet = dtx * 0.2 + dgk * 0.3 + dck * 0.5;

        DiemCaNhan d = new DiemCaNhan();
        d.setMaSV(sv.getMaSV());
        d.setMaMH(request.getParameter("maMH"));
        d.setHocKy(request.getParameter("hocKy"));
        d.setDiemThuongXuyen(dtx);
        d.setDiemGiuaKy(dgk);
        d.setDiemCuoiKy(dck);
        d.setDiemTongKet(tongKet);

        if (diemID != null && !diemID.isEmpty()) {
            d.setDiemID(Integer.parseInt(diemID));
            diemDAO.updateByIdAndMaSV(d);
        } else {
            diemDAO.insert(d);
        }

        response.sendRedirect(request.getContextPath() + "/nhapdiem");
    }

    // Chuyển String sang double an toàn
    private double toDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
