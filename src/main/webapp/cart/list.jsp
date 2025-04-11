<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Giỏ Hàng Của Bạn</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    .cart-header {
      background: linear-gradient(45deg, #007bff, #00b4d8);
      color: white;
      padding: 1rem;
      border-radius: 10px 10px 0 0;
    }
    .table {
      box-shadow: 0 0 20px rgba(0,0,0,0.1);
      border-radius: 10px;
      overflow: hidden;
    }
    .total-price {
      font-size: 1.5rem;
      font-weight: bold;
      color: #dc3545;
    }
    .btn-checkout {
      background: linear-gradient(45deg, #28a745, #20c997);
      border: none;
      padding: 10px 30px;
    }
    .btn-checkout:hover {
      background: linear-gradient(45deg, #218838, #1bae87);
    }
    .btn-back {
      background: linear-gradient(45deg, #6c757d, #adb5bd);
      border: none;
      padding: 10px 30px;
    }
    .btn-back:hover {
      background: linear-gradient(45deg, #5a6268, #9ea7ad);
    }
  </style>
</head>
<body>
<div class="container my-5">
  <div class="cart-header">
    <h2><i class="fas fa-shopping-cart me-2"></i>Giỏ Hàng Của Bạn</h2>
  </div>

  <c:if test="${empty sessionScope.cart}">
    <div class="alert alert-info mt-3" role="alert">
      Giỏ hàng của bạn đang trống. <a href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>.
    </div>
  </c:if>

  <c:if test="${not empty sessionScope.cart}">
    <table class="table table-hover mt-3">
      <thead class="table-dark">
      <tr>
        <th><i class="fas fa-box me-2"></i>Product Name</th>
        <th><i class="fas fa-tag me-2"></i>Price</th>
        <th><i class="fas fa-sort-numeric-up me-2"></i>Quantity</th>
        <th><i class="fas fa-money-bill me-2"></i>Subtotal</th>
      </tr>
      </thead>
      <tbody>
      <c:set var="totalPrice" value="${0}"/>
      <c:forEach var="item" items="${sessionScope.cart}">
        <tr>
          <td class="align-middle">${item.product.name}</td>
          <td class="align-middle">$${item.product.price}</td>
          <td class="align-middle">
            <input type="number" class="form-control w-50 d-inline"
                   value="${item.quantity}" min="1" name="quantity" readonly>
          </td>
          <td class="align-middle">$${item.quantity * item.product.price}</td>
        </tr>
        <c:set var="totalPrice" value="${totalPrice + (item.quantity * item.product.price)}"/>
      </c:forEach>
      </tbody>
    </table>

    <div class="d-flex justify-content-end mt-4">
      <p class="total-price">
        <i class="fas fa-wallet me-2"></i>Total: $${totalPrice}
      </p>
    </div>

    <div class="d-flex justify-content-end mt-3">
      <a href="${pageContext.request.contextPath}/products" class="btn btn-back text-white me-2">
        <i class="fas fa-arrow-left me-2"></i>Quay lại Trang chủ
      </a>
      <a href="${pageContext.request.contextPath}/carts?action=checkout" class="btn btn-checkout text-white">
        <i class="fas fa-check me-2"></i>Checkout
      </a>
    </div>
  </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>