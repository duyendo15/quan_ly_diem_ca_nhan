<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MonHoc" %>
<%@ page import="model.DiemCaNhan" %>
<%@ page import="model.SinhVien" %>

<%
    List<MonHoc> dsMon = (List<MonHoc>) request.getAttribute("dsMon");
    List<DiemCaNhan> dsDiem = (List<DiemCaNhan>) request.getAttribute("dsDiem");
    SinhVien sv = (SinhVien) session.getAttribute("sinhVien");
    DiemCaNhan diemEdit = (DiemCaNhan) request.getAttribute("diemEdit");
    boolean isEdit = (diemEdit != null);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập điểm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">

    <!-- Header + nút trang chủ -->
    <div class="d-flex justify-content-between mb-3">
        <h3>Nhập điểm cá nhân</h3>
        <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-secondary btn-sm">← Trang chủ</a>
    </div>

    <!-- Thông tin sinh viên -->
    <div class="card mb-3">
        <div class="card-body">
            <p><strong>Mã SV:</strong> <%= sv.getMaSV() %></p>
            <p><strong>Họ tên:</strong> <%= sv.getHoTen() %></p>
        </div>
    </div>

    <!-- Form nhập/sửa điểm -->
    <div class="card mb-4">
        <div class="card-header"><%= isEdit ? "Cập nhật điểm môn học" : "Thêm điểm môn học" %></div>
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/nhapdiem" method="post" class="row g-3">

                <input type="hidden" name="diemID" value="<%= isEdit ? diemEdit.getDiemID() : "" %>">

                <!-- Môn học -->
                <div class="col-md-4">
                    <label class="form-label">Môn học</label>
                    <select name="maMH" class="form-select" required>
                        <option value="">-- Chọn môn --</option>
                        <% for (MonHoc m : dsMon) {
                               String selected = (isEdit && diemEdit.getMaMH().equals(m.getMaMH())) ? "selected" : ""; %>
                        <option value="<%= m.getMaMH() %>" <%= selected %>>
                            <%= m.getMaMH() %> - <%= m.getTenMonHoc() %>
                        </option>
                        <% } %>
                    </select>
                </div>

                <!-- Học kỳ -->
                <div class="col-md-2">
                    <label class="form-label">Học kỳ</label>
                    <input type="text" name="hocKy" class="form-control"
                           placeholder="2024-2025 HK1"
                           value="<%= isEdit ? diemEdit.getHocKy() : "" %>" required>
                </div>

                <!-- Điểm TX -->
                <div class="col-md-2">
                    <label class="form-label">Điểm TX</label>
                    <input type="number" step="0.1" min="0" max="10"
                           name="diemTX" class="form-control"
                           value="<%= isEdit ? diemEdit.getDiemThuongXuyen() : "" %>" required>
                </div>

                <!-- Điểm GK -->
                <div class="col-md-2">
                    <label class="form-label">Điểm GK</label>
                    <input type="number" step="0.1" min="0" max="10"
                           name="diemGK" class="form-control"
                           value="<%= isEdit ? diemEdit.getDiemGiuaKy() : "" %>" required>
                </div>

                <!-- Điểm CK -->
                <div class="col-md-2">
                    <label class="form-label">Điểm CK</label>
                    <input type="number" step="0.1" min="0" max="10"
                           name="diemCK" class="form-control"
                           value="<%= isEdit ? diemEdit.getDiemCuoiKy() : "" %>" required>
                </div>

                <!-- Nút submit -->
                <div class="col-12">
                    <button type="submit" class="btn btn-success">
                        <%= isEdit ? "Cập nhật điểm" : "Lưu điểm" %>
                    </button>
                    <% if (isEdit) { %>
                        <a href="<%=request.getContextPath()%>/nhapdiem" class="btn btn-outline-secondary ms-2">
                            Hủy chỉnh sửa
                        </a>
                    <% } %>
                    <a href="<%=request.getContextPath()%>/bangdiem" class="btn btn-link ms-2">
                        Xem bảng điểm & GPA
                    </a>
                </div>

            </form>
        </div>
    </div>

    <!-- Bảng danh sách điểm -->
    <div class="card">
        <div class="card-header">Danh sách điểm đã nhập</div>
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
                    <th>Hành động</th>
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
                    <td>
                        <a href="<%=request.getContextPath()%>/nhapdiem?action=edit&id=<%= d.getDiemID() %>"
                           class="btn btn-sm btn-warning">Sửa</a>
                        <a href="<%=request.getContextPath()%>/nhapdiem?action=delete&id=<%= d.getDiemID() %>"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Xóa điểm môn này?');">Xóa</a>
                    </td>
                </tr>
                <%   } 
                   } else { %>
                <tr>
                    <td colspan="7" class="text-center">Chưa có điểm nào.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

</div>
</body>
</html>
