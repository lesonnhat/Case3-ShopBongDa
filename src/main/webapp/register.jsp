<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: -webkit-box;
            display: flex;
            -ms-flex-align: center;
            -ms-flex-pack: center;
            -webkit-box-align: center;
            align-items: center;
            -webkit-box-pack: center;
            justify-content: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .form-signin .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="text"] {
            margin-bottom: -2px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="email"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body class="text-center">
<form class="form-signin" action="register" method="post">
    <img class="mb-4" src="images/man-utd.png" alt="Manchester United Logo" width="90" height="90">
    <h1 class="h3 mb-3 font-weight-normal">Đăng ký tài khoản</h1>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Tên đăng nhập" required autofocus aria-label="Username">
    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Mật khẩu" required aria-label="Password">
    <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email" required aria-label="Email">
    <button class="btn btn-lg btn-primary w-100" type="submit">Đăng ký</button>
    <p class="mt-3">Bạn đã có tài khoản? <a href="index.jsp">Đăng nhập</a></p>
    <p class="mt-5 mb-3 text-muted">© 2025. Tổng đài hỗ trợ: 0963.369.134</p>
</form>
</body>
</html>