<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta charset="ISO-8859-1">
    <title>Products</title>
</head>
<body>
<div class="container">
    <div style=" margin: 0px auto;
    text-align: right;
    padding-top: 20px; padding-bottom: 30px;">
        <a href="/e-shop/logout" class="btn btn-sm btn-primary">Logout</a>
        <div style="text-align: center;">
            <h1>
                Welcome, <c:out value="${user.name}"/><BR>
                <c:out value="${user.email}"/><BR>
            </h1>
        </div>
    </div>
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><a href="/e-shop/products?sort=id">ID</a></th>
            <th scope="col"><a href="/e-shop/products?sort=title">Product</a></th>
            <th scope="col"><a href="/e-shop/products?sort=price">Price</a></th>
            <th scope="col"><a href="/e-shop/products?sort=discount">Discount</a></th>
            <th scope="col"><a href="/e-shop/products?sort=likes">Popularity</a></th>
            <c:if test="${user.userRole eq 'AUTHORIZED_USER'}">
                <th scope="col">Status</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr scope="row">
                <td><c:out value="${product.id}"/></td>
                <td><a href="/e-shop/products/${product.id}">
                    <c:out value="${product.title}"/></a></td>
                <td><c:out value="${product.price}"/></td>
                <td><c:out value="${product.discount}"/></td>
                <td><c:out value="${product.likes}"/></td>
                <c:if test="${! product.users.contains(user) and user.userRole eq 'AUTHORIZED_USER'}">
                    <td><a href="/e-shop/products/${product.id}/like">ADDED</a></td>
                </c:if>
                <c:if test="${product.users.contains(user) and user.userRole  eq 'AUTHORIZED_USER'}">
                    <td><a href="/e-shop/products/${product.id}/unlike">DELETE</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="container">
        <div style=" margin: 0px auto;text-align: center;">
            <c:if test="${user.userRole eq 'ADMIN'}">
                <a href="/e-shop/products/new" class="btn btn-lg btn-primary">Add New Product</a>
                <a href="/e-shop/orders/admin" class="btn btn-lg btn-primary">My Cabinet</a>
            </c:if>
        </div>
        <div style=" margin: 0px auto;text-align: center;">
            <c:if test="${user.userRole eq 'MANAGER'}">
                <a href="/e-shop/products/new" class="btn btn-lg btn-primary">Add New Product</a>
                <a href="/e-shop/orders/manager" class="btn btn-lg btn-primary">My Cabinet</a>
            </c:if>
        </div>
        <div style=" margin: 0px auto;text-align: center;">
            <c:if test="${user.userRole eq 'AUTHORIZED_USER'}">
                <a href="/e-shop/orders/customer" class="btn btn-lg btn-primary">My Cabinet</a>
            </c:if>
        </div>
        <p> Page ${currentPage} from ${pageProduct.totalPages}  </p>
        <h6>
            <br>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="/e-shop/products?sort=${sort}&page=${currentPage - 1}&size=5">Previous</a>
                        </li>
                    </c:if>
                    <c:if test="${currentPage < pageProduct.totalPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="/e-shop/products?sort=${sort}&page=${currentPage + 1}&size=5">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </h6>
    </div>
</div>
</body>
</html>