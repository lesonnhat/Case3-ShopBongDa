<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>The Best 34</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        html {
            scroll-behavior: smooth;
        }
        body {
            font-family: "Be Vietnam Pro";
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            padding-top: 56px; /* Đẩy nội dung xuống để tránh bị navbar che */
        }
        h2 {
            font-size: 24px;
            font-weight: bold;
        }
        h3 {
            font-size: 18px;
            margin: 0px 0px 20px 0px;
        }
        h4 {
            font-size: 18px;
        }
        .container {
            flex: 1;
        }
        footer {
            flex-shrink: 0;
        }
        .bg-secondary {
            flex-shrink: 0;
        }
        .navbar {
            background-color: #181818;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }
        .navbar-brand, .nav-link {
            color: white !important;
        }
        .nav-link:hover {
            background-color: sandybrown;
        }
        .navbar-toggler {
            background-color: white !important;
        }
        .dropdown-menu {
            background-color: #181818;
            border: none;
            display: none;
        }
        .dropdown-item {
            color: white;
        }
        .dropdown-item:hover {
            background-color: sandybrown;
        }
        .nav-item.dropdown:hover .dropdown-menu {
            display: block;
        }
        .welcome-text {
            color: white !important; /* Màu trắng cố định */
            margin-right: 5px; /* Khoảng cách với Đăng xuất */
            cursor: default; /* Không hiển thị con trỏ như link */
        }
        /* Loại bỏ hover trên welcome-text */
        .welcome-text:hover {
            background-color: transparent !important;
            color: white !important;
        }
        .product-card {
            transition: all 0.3s ease;
        }
        .product-card:hover {
            border: 2px solid #007bff;
            box-shadow: 0 0 10px rgba(0, 123, 255, 0.3);
        }
        .product-image-wrapper {
            position: relative;
            width: 100%;
            padding-top: 100%;
        }
        .card-img-top {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .news-text:hover {
            color: #007bff !important;
            transition: color 0.3s ease;
        }
        .add-to-cart {
            padding: 10px;
            font-size: 14px;
            text-decoration: none;
            position: relative;
            border: none;
            border-radius: 30px;
            background-color: #181818;
            color: white;
        }
        .add-to-cart:hover {
            cursor: pointer;
            color: white;
            background-color: sandybrown;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/products">TRANG CHỦ</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">GIỚI THIỆU</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button">
                        SẢN PHẨM
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#category-ao">Áo bóng đá</a></li>
                        <li><a class="dropdown-item" href="#category-giay">Giày bóng đá</a></li>
                        <li><a class="dropdown-item" href="#category-tui">Balo/Túi</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#footer">LIÊN HỆ</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link welcome-text" href="javascript:void(0)">Xin chào, ${sessionScope.username}!</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">
                        <i class="bi bi-box-arrow-right me-1"></i>Đăng xuất
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="bg-light text-center pt-4 pb-3">
    <h4 class="fw-bold">BEST SALE - NHẬN NGAY ƯU ĐÃI 30%</h4>
    <h4>SHOPPING THÔI</h4>
</div>

<div class="container mt-5 mb-4">
    <div class="row">
        <!-- Content -->
        <div class="col-lg-9 inner-container">
            <!-- Tự định nghĩa danh sách danh mục theo thứ tự bạn muốn -->
            <c:set var="customCategories" value="${['ao', 'giay', 'tui']}" />

            <!-- Lặp qua từng danh mục trong danh sách tùy chỉnh -->
            <c:forEach var="category" items="${customCategories}">
                <!-- Chỉ hiển thị danh mục nếu có sản phẩm thuộc danh mục đó -->
                <c:set var="hasProducts" value="false" />
                <c:forEach var="p" items="${listProducts}">
                    <c:if test="${p.getCategory() == category}">
                        <c:set var="hasProducts" value="true" />
                    </c:if>
                </c:forEach>

                <!-- Nếu danh mục có sản phẩm, hiển thị trong một div riêng với id -->
                <c:if test="${hasProducts}">
                    <!-- Div riêng cho từng danh mục, có id duy nhất -->
                    <div id="category-${category}" class="category-container mb-4">
                        <div class="category-section">
                            <!-- Hiển thị tên danh mục tùy chỉnh -->
                            <h2 class="mb-4 text-capitalize">
                                <c:choose>
                                    <c:when test="${category == 'ao'}">Áo bóng đá</c:when>
                                    <c:when test="${category == 'giay'}">Giày bóng đá</c:when>
                                    <c:when test="${category == 'tui'}">Balo/Túi</c:when>
                                    <c:otherwise>${category}</c:otherwise>
                                </c:choose>
                            </h2>
                            <div class="row">
                                <!-- Lọc và hiển thị sản phẩm thuộc danh mục hiện tại -->
                                <c:forEach var="p" items="${listProducts}">
                                    <c:if test="${p.getCategory() == category}">
                                        <div class="col-md-4 mb-4">
                                            <div class="card h-100 text-center position-relative product-card">
                                                <div class="product-image-wrapper">
                                                    <img src="<c:out value='${p.getImageUrl()}'/>" class="card-img-top" alt="Product Image">
                                                </div>
                                                <div class="card-body">
                                                    <h5 class="card-title"><c:out value="${p.getName()}"/></h5>
                                                    <p class="card-text fw-bold" style="font-size: 20px"><c:out value="${p.getPrice()}"/>
                                                        <span class="currency" style="font-weight: normal">$</span>
                                                    </p>
                                                    <button class="btn add-to-cart" onclick="window.location.href='${pageContext.request.contextPath}/carts?action=add&id=${p.getId()}'">
                                                        <i class="fa-solid fa-cart-shopping me-2"></i>Thêm Vào Giỏ
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <!-- Sidebar -->
        <div class="col-lg-3">
            <h2 class="mb-4">Tin tức</h2>
            <div class="mb-3">
                <div class="row">
                    <div class="col-4">
                        <img src="images/news-01.jpg" class="img-fluid" alt="News Image">
                    </div>
                    <div class="col-8">
                        <a href="#" class="news-text text-decoration-none text-dark">Declan Rice lập kỷ lục trong ngày Arsenal đè bẹp Real Madrid</a>
                    </div>
                </div>
            </div>
            <div class="mb-3">
                <div class="row">
                    <div class="col-4">
                        <img src="images/news-03.jpg" class="img-fluid" alt="News Image">
                    </div>
                    <div class="col-8">
                        <a href="#" class="news-text text-decoration-none text-dark">MU chuẩn bị sẵn 200 triệu euro để nổ ‘bom tấn’ Vinicius</a>
                    </div>
                </div>
            </div>
            <div class="mb-3">
                <div class="row">
                    <div class="col-4">
                        <img src="images/news-02.jpg" class="img-fluid" alt="News Image">
                    </div>
                    <div class="col-8">
                        <a href="#" class="news-text text-decoration-none text-dark">Ông Park Hang-seo làm phó chủ tịch LĐBĐ Hàn Quốc</a>
                    </div>
                </div>
            </div>
            <div>
                <a href="tel:0963369134">
                    <img src="images/hotline.png" class="img-fluid" alt="Hotline">
                </a>
            </div>
        </div>
    </div>
</div>

<footer class="bg-dark text-white pt-5 pb-5" id="footer">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h3>LIÊN HỆ</h3>
                <p><strong>CÔNG TY CỔ PHẦN THƯƠNG MẠI THE BEST</strong></p>
                <p>• Địa chỉ: Xã Thanh Cường, Huyện Thanh Hà, Tỉnh Hải Dương</p>
                <p>• Hotline: 0963.369.134</p>
                <p>• Email: sonnhatbest@gmail.com</p>
            </div>
            <div class="col-md-4">
                <h3>GOOGLE MAP</h3>
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d23001.185413879957!2d106.46762512384895!3d20.
                847612519921036!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135896d380e305d%3A0xbdf209f9952407cf!
                2zVHLGsOG7nW5nIFRIUFQgSMOgIMSQw7RuZw!5e0!3m2!1svi!2s!4v1744184876329!5m2!1svi!2s"
                        width="100%" height="200" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            </div>
            <div class="col-md-4">
                <h3>MẠNG XÃ HỘI</h3>
                <div>
                    <a target="_blank" href="https://www.facebook.com/lesonnhat/" rel="noopener"><img src="images/social-fb.png"></a>
                    &nbsp;
                    &nbsp;
                    <a target="_blank" href="https://www.youtube.com/@lesonnhat" rel="noopener"><img src="images/social-yt.png"></a>
                    &nbsp;
                    &nbsp;
                    <a target="_blank" href="https://www.instagram.com/lesonnhat/" rel="noopener"><img src="images/social-insta.png"></a>
                </div>
            </div>
        </div>
    </div>
</footer>

<div class="bg-secondary text-center py-2 text-white">
    <p class="m-0">© 2025. Bản quyền thuộc về The Best 34</p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>