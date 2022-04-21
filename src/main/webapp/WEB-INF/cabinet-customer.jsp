<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<style>
    .pagination {
        display: inline-block;
    }
</style>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <meta charset="ISO-8859-1">
    <title>Products</title>
</head>
<body>
<div class="container">
    <div style=" margin: 0px auto;
    text-align: right;
    padding-top: 20px; padding-bottom: 30px;">
        <a href="/e-shop/logout" class="btn btn-sm btn-primary">Logout</a>
        <br><c:out value="${products[0].user.email}"/>
        <h1 class="h2 mb-3 font-weight-normal" style="padding-top: 20px; text-align: left;">
            MY ORDERS
        </h1>
    </div>
</div>
<div class="container">
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Title</th>
            <th scope="col">CreatedAt</th>
            <th scope="col">Price</th>
            <th scope="col">Discount</th>
            <th scope="col">Processing</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
                <tr scope="row">
                    <td><c:out value="${product.product.id}"/></td>
                    <td><c:out value="${product.user.email}"/></td>
                    <td><c:out value="${product.product.title}"/></td>
                    <td><c:out value="${product.product.price}"/></td>
                    <td><c:out value="${product.product.discount}" default=""/></td>
                    <td><c:out value="${product.processStatus}" default=""/></td>
                </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div style=" margin: 0px auto;text-align: center;">
    <a href="/e-shop/products" class="btn btn-lg btn-primary">Continue shopping</a>
</div>
<div class="container">
    <p> Page ${currentPage} from ${pageLikedProducts.totalPages}  </p>
    <br>
    <h6>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item"><a class="page-link"
                                             href="/e-shop/orders/customer?sort=${sort}&page=${currentPage - 1}&size=5">Previous</a>
                    </li>
                </c:if>
                <c:if test="${currentPage < pageLikedProducts.totalPages}">
                    <li class="page-item"><a class="page-link"
                                             href="/e-shop/orders/customer?sort=${sort}&page=${currentPage + 1}&size=5">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </h6>
</div>
</body>
</html>