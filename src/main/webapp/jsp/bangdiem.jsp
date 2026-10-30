<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.DiemCaNhan" %>
<%@ page import="model.SinhVien" %>
<%
    List<DiemCaNhan> dsDiem = (List<DiemCaNhan>) request.getAttribute("dsDiem");
    Double gpa = (Double) request.getAttribute("gpa");
    List<String> dsHocKy = (List<String>) request.getAttribute("dsHocKy");
    String selectedHocKy = (String) request.getAttribute("selectedHocKy");
    if (selectedHocKy == null) selectedHocKy = "";
    SinhVien sv = (SinhVien) session.getAttribute("sinhVien");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điểm & GPA</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f5f5f5; min-height: 100vh; display: flex; flex-direction: column; }
        .main-content { flex: 1; padding: 20px 0; }
    </style>
</head>
<body>

<div class="main-content">
    <div class="container">

        <!-- Header + nút về trang chủ -->
        <div class="d-flex justify-content-between mb-3">
            <h3>Bảng điểm cá nhân</h3>
            <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-secondary btn-sm">← Trang chủ</a>
        </div>

        <!-- Thông tin sinh viên & chọn học kỳ -->
        <div class="card mb-3">
            <div class="card-body row g-3 align-items-end">
                <div class="col-md-4">
                    <p><strong>Mã SV:</strong> <%= sv.getMaSV() %></p>
                    <p><strong>Họ tên:</strong> <%= sv.getHoTen() %></p>
                </div>

                <div class="col-md-4">
                    <form method="get" action="<%=request.getContextPath()%>/bangdiem">
                        <label class="form-label">Chọn học kỳ</label>
                        <select name="hocKy" class="form-select" onchange="this.form.submit()">
                            <option value="">-- Tất cả học kỳ --</option>
                            <% if (dsHocKy != null) {
                                for (String hk : dsHocKy) {
                                    String sel = hk.equals(selectedHocKy) ? "selected" : "";
                            %>
                            <option value="<%= hk %>" <%= sel %>><%= hk %></option>
                            <%  } } %>
                        </select>
                    </form>
                </div>

                <div class="col-md-4">
                    <p><strong>GPA (thang 10):</strong> <%= String.format("%.2f", gpa != null ? gpa : 0) %></p>
                    <p class="text-muted mb-0">
                        <% if (selectedHocKy.isEmpty()) { %>
                            (Tất cả các học kỳ)
                        <% } else { %>
                            (Chỉ cho: <strong><%= selectedHocKy %></strong>)
                        <% } %>
                    </p>
                </div>
            </div>
        </div>

        <!-- Bảng điểm -->
        <div class="card">
            <div class="card-header">
                Danh sách môn học
                <% if (!selectedHocKy.isEmpty()) { %> - Học kỳ: <strong><%= selectedHocKy %></strong><% } %>
            </div>
            <div class="card-body p-0">
                <table class="table table-bordered table-striped mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Môn học</th>
                            <th>Tín chỉ</th>
                            <th>Học kỳ</th>
                            <th>TX</th>
                            <th>GK</th>
                            <th>CK</th>
                            <th>Tổng kết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (dsDiem != null && !dsDiem.isEmpty()) {
                            for (DiemCaNhan d : dsDiem) { %>
                                <tr>
                                    <td><%= d.getTenMonHoc() %></td>
                                    <td><%= d.getSoTinChi() %></td>
                                    <td><%= d.getHocKy() %></td>
                                    <td><%= d.getDiemThuongXuyen() %></td>
                                    <td><%= d.getDiemGiuaKy() %></td>
                                    <td><%= d.getDiemCuoiKy() %></td>
                                    <td><%= d.getDiemTongKet() %></td>
                                </tr>
                        <%  } } else { %>
                            <tr><td colspan="7" class="text-center">Không có dữ liệu điểm.</td></tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<footer class="bg-dark text-white text-center py-3">
    © 2025 - Hệ thống quản lý điểm học tập
</footer>

</body>
</html>
