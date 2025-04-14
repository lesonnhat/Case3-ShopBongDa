<%-- product/list.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Danh Sách Sản Phẩm</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <style>
    body {
      padding-top: 40px;
      padding-bottom: 40px;
      background-color: #f5f5f5;
    }
    .container {
      max-width: 960px;
    }
  </style>
</head>
<body>
<div class="container">
  <h1 class="text-center">Danh Sách Sản Phẩm</h1>

  <!-- Hiển thị nút "Manage Products" chỉ khi người dùng là admin -->
  <c:if test="${sessionScope.role == 'admin'}">
    <a href="/admin?action=create" class="btn btn-primary mb-3">Thêm Sản Phẩm</a>
  </c:if>

  <table class="table table-bordered">
    <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Price</th>
      <th>Origin</th>
      <th>Image URL</th>
      <th>Category</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${listProducts}">
      <tr>
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.price}</td>
        <td>${product.origin}</td>
        <td>${product.imageUrl}</td>
        <td>${product.category}</td>
        <td>
          <a href="/admin?action=edit&id=${product.id}">Sửa</a>
          <a href="/admin?action=delete&id=${product.id}">Xóa</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Đăng Xuất</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>