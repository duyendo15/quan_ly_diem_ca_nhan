<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="mb-3 text-center">Đăng nhập</h3>

                    <%
                        String error = (String) request.getAttribute("error");
                        String msg = (String) request.getAttribute("message");
                        if (msg != null) {
                    %>
                    <div class="alert alert-success"><%= msg %></div>
                    <% } %>
                    <% if (error != null) { %>
                    <div class="alert alert-danger"><%= error %></div>
                    <% } %>

                    <form action="<%=request.getContextPath()%>/login" method="post" accept-charset="UTF-8">
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                    </form>

                    <hr>
                    <p class="text-center mb-0">
                        Chưa có tài khoản?
                        <a href="<%=request.getContextPath()%>/register">Đăng ký ngay</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
