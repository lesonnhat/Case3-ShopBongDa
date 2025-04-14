<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa Sản Phẩm</title>
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
</head>
<body>
<div class="container">
    <h1 class="text-center" style="align-content: center">Sửa Sản Phẩm</h1>

    <!-- Hiển thị nút "Manage Products" chỉ khi người dùng là admin -->
    <c:if test="${sessionScope.role == 'admin'}">
    <div align="center">
        <a href="/admin" class="btn btn-primary mt-3 mb-4">Danh Sách Sản Phẩm</a>
    </div>
    </c:if>

    <div align="center">
        <form method="post" action="/admin?action=edit">
            <table border="1" cellpadding="5">
                <c:if test="${product != null}">
                <input type="hidden" name="id" value="<c:out value='${product.id}' />"/>
                </c:if>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name"
                               value="<c:out value="${product.name}" />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Price:</th>
                    <td>
                        <input type="text" name="price" id="price"
                               value="<c:out value="${product.price}" />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Origin:</th>
                    <td>
                        <input type="text" name="origin"
                               value="<c:out value="${product.origin}" />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Image Url:</th>
                    <td>
                        <input type="text" name="imageUrl"
                               value="<c:out value="${product.imageUrl}" />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Category:</th>
                    <td>
                        <select id="category" name="category" class="form-select-button" required>
                            <option value="ao" ${product.category == 'ao' ? 'selected' : ''}>Áo</option>
                            <option value="giay" ${product.category == 'giay' ? 'selected' : ''}>Giày</option>
                            <option value="tui" ${product.category == 'tui' ? 'selected' : ''}>Túi</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Lưu"/>
                    </td>
                </tr>
            </table>
        </form>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mt-4">Đăng Xuất</a>
    </div>
</div>
</body>
</html>
