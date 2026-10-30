<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MonHoc" %>
<%
    List<MonHoc> ds = (List<MonHoc>) request.getAttribute("dsMonHoc");
    MonHoc mEdit = (MonHoc) request.getAttribute("monHocEdit");
    boolean isEdit = (mEdit != null);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý môn học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">

    <!-- Header + nút trang chủ -->
    <div class="d-flex justify-content-between mb-3">
        <h3>Quản lý môn học</h3>
        <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-secondary btn-sm">← Trang chủ</a>
    </div>

    <!-- Form thêm/sửa môn học -->
    <div class="card mb-4">
        <div class="card-header"><%= isEdit ? "Cập nhật môn học" : "Thêm môn học mới" %></div>
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/monhoc" method="post" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã môn học</label>
                    <input type="text" name="maMH" class="form-control"
                           value="<%= isEdit ? mEdit.getMaMH() : "" %>"
                           <%= isEdit ? "readonly" : "" %> required>
                </div>
                <div class="col-md-5">
                    <label class="form-label">Tên môn học</label>
                    <input type="text" name="tenMonHoc" class="form-control"
                           value="<%= isEdit ? mEdit.getTenMonHoc() : "" %>" required>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Số tín chỉ</label>
                    <input type="number" name="soTinChi" class="form-control" min="1"
                           value="<%= isEdit ? mEdit.getSoTinChi() : "" %>" required>
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <input type="hidden" name="mode" value="<%= isEdit ? "update" : "add" %>">
                    <button type="submit" class="btn btn-primary w-100">
                        <%= isEdit ? "Cập nhật" : "Thêm mới" %>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Bảng danh sách môn học -->
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Mã MH</th>
            <th>Tên môn học</th>
            <th>Số tín chỉ</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <% if (ds != null && !ds.isEmpty()) {
            for (MonHoc m : ds) { %>
            <tr>
                <td><%= m.getMaMH() %></td>
                <td><%= m.getTenMonHoc() %></td>
                <td><%= m.getSoTinChi() %></td>
                <td>
                    <a href="<%=request.getContextPath()%>/monhoc?action=edit&maMH=<%=m.getMaMH()%>"
                       class="btn btn-sm btn-warning">Sửa</a>
                    <a href="<%=request.getContextPath()%>/monhoc?action=delete&maMH=<%=m.getMaMH()%>"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Xóa môn này?');">Xóa</a>
                </td>
            </tr>
        <%  }
           } else { %>
            <tr>
                <td colspan="4" class="text-center">Chưa có môn học nào.</td>
            </tr>
        <% } %>
        </tbody>
    </table>

</div>

</body>
</html>
