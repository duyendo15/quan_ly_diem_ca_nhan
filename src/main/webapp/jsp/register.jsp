<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="mb-3 text-center">Đăng ký tài khoản</h3>

                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %>
                    <div class="alert alert-danger"><%= error %></div>
                    <% } %>

                    <form action="<%=request.getContextPath()%>/register" method="post" accept-charset="UTF-8">
                        <div class="mb-3">
                            <label class="form-label">Mã sinh viên</label>
                            <input type="text" name="maSV" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input type="text" name="hoTen" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Đăng ký</button>
                    </form>

                    <hr>
                    <p class="text-center mb-0">
                        Đã có tài khoản?
                        <a href="<%=request.getContextPath()%>/login">Đăng nhập</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
