<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
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
        <a href="/e-shop/redirect" class="btn btn-sm btn-primary">Register/Login</a>
        <div style="text-align: center;">
            <h1>
                PRODUCTS
            </h1>
        </div>
    </div>
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><a href="/e-shop/?sort=id">ID</a></th>
            <th scope="col"><a href="/e-shop/?sort=title">Title</a></th>
            <th scope="col"><a href="/e-shop/?sort=price">Price</a></th>
            <th scope="col"><a href="/e-shop/?sort=discount">Discount</a></th>
            <th scope="col"><a href="/e-shop/?sort=likes">Popularity</a></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr scope="row">
                <td><c:out value="${product.id}"/></td>
                <td>
                    <a href="/e-shop/redirect">
                        <c:out value="${product.title}"/>
                    </a>
                </td>
                <td><c:out value="${product.price}"/></td>
                <td><c:out value="${product.discount}" default=""/></td>
                <td><c:out value="${product.likes}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="container">
    <p> Page ${currentPage} from ${pageProduct.totalPages}  </p>
    <br>
    <h6>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item"><a class="page-link" href="/e-shop/?sort=${sort}&page=${currentPage - 1}&size=5">Previous</a>
                    </li>
                </c:if>
                <c:if test="${currentPage < pageProduct.totalPages}">
                    <li class="page-item"><a class="page-link"
                                             href="/e-shop/?sort=${sort}&page=${currentPage + 1}&size=5">Next</a></li>
                </c:if>
            </ul>
        </nav>
    </h6>
</div>
</body>
</html>