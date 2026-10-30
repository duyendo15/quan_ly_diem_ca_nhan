<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.SinhVien" %>
<%
    SinhVien sv = (SinhVien) session.getAttribute("sinhVien");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý điểm học tập</title>

   
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        html, body {
            height: 100%;
        }

        body {
            background-color: #f4f6f9; /* nền xám nhạt */
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", Tahoma, sans-serif;
        }

        .main-content {
            flex: 1;
        }

      
        .navbar {
            background-color: #1f2937 !important; 
        }

        /* Hero */
        .hero {
            background: #ffffff;
            border-radius: 14px;
            padding: 40px 20px;
            box-shadow: 0 6px 16px rgba(0,0,0,0.06);
        }

        .hero h1 {
            color: #1f2937;
            font-weight: 700;
        }

        /* Card */
        .card {
            border: none;
            border-radius: 14px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: all 0.25s ease;
        }

        .card-hover:hover {
            transform: translateY(-6px);
            box-shadow: 0 12px 28px rgba(0,0,0,0.15);
        }

        .card-body i {
            font-size: 42px;
            color: #2563eb; 
        }

        .card-body h5 {
            margin-top: 15px;
            margin-bottom: 18px;
            color: #1f2937;
            font-weight: 600;
        }

      
        .btn-primary,
        .btn-success,
        .btn-warning {
            background-color: #2563eb;
            border: none;
            color: #fff;
        }

        .btn-primary:hover,
        .btn-success:hover,
        .btn-warning:hover {
            background-color: #1e40af;
        }

        /* Footer */
        footer {
            background-color: #1f2937;
            font-size: 14px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark shadow">
    <div class="container">
        <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/index.jsp">
            <i class="bi bi-mortarboard-fill"></i> QL Điểm Học Tập
        </a>
        <div class="ms-auto">
            <% if (sv == null) { %>
                <a href="<%=request.getContextPath()%>/login" class="btn btn-outline-light me-2">Đăng nhập</a>
                <a href="<%=request.getContextPath()%>/register" class="btn btn-light text-dark">Đăng ký</a>
            <% } else { %>
                <span class="text-white me-3">
                    Xin chào, <strong><%= sv.getHoTen() %></strong>
                </span>
                <a href="<%=request.getContextPath()%>/logout" class="btn btn-outline-light">Đăng xuất</a>
            <% } %>
        </div>
    </div>
</nav>

<div class="main-content">

    <!-- Hero -->
    <div class="container py-5">
        <div class="hero text-center">
            <h1>Hệ thống theo dõi kết quả học tập</h1>
            <p class="text-muted mt-2">
                Quản lý môn học, nhập điểm và theo dõi GPA một cách hiệu quả
            </p>
        </div>
    </div>

    <!-- Card chức năng -->
    <div class="container pb-5">
        <div class="row g-4">

            <div class="col-md-4">
                <div class="card card-hover h-100 text-center">
                    <div class="card-body">
                        <i class="bi bi-journal-bookmark-fill"></i>
                        <h5>Quản lý môn học</h5>
                        <a href="<%=request.getContextPath()%>/monhoc" class="btn btn-primary">
                            Mở trang
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card card-hover h-100 text-center">
                    <div class="card-body">
                        <i class="bi bi-pencil-square"></i>
                        <h5>Nhập điểm</h5>
                        <a href="<%=request.getContextPath()%>/nhapdiem" class="btn btn-primary">
                            Mở trang
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card card-hover h-100 text-center">
                    <div class="card-body">
                        <i class="bi bi-graph-up-arrow"></i>
                        <h5>Bảng điểm & GPA</h5>
                        <a href="<%=request.getContextPath()%>/bangdiem" class="btn btn-primary">
                            Mở trang
                        </a>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

<footer class="text-white text-center py-3">
    © 2025 - Hệ thống quản lý điểm học tập
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
