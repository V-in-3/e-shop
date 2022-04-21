<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<style>
    .pagination {
        display: inline-block;
    }

    .pagination a {
        color: black;
        float: left;
        padding: 5px 5px;
        text-decoration: none;
    }

    .pagination a.active {
        background-color: gray;
        color: white;
        border-radius: 2px;
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
        <a href="/e-shop/products" class="btn btn-sm btn-primary">Back</a>
        <a href="/e-shop/logout" class="btn btn-sm btn-primary">Logout</a>
        <br>
        <h1 class="h2 mb-3 font-weight-normal" style="padding-top: 20px; text-align: left;">
            ORDERS FOR PROCESSING
        </h1>
    </div>
</div>
<div class="container">

    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col">OrderID</th>
            <th scope="col">Customer</th>
            <th scope="col">Title</th>
            <th scope="col">Price</th>
            <th scope="col">Discount</th>
            <th scope="col">Status</th>
            <th scope="col">Processing</th>
            <th scope="col">Saving</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="order">
            <tr>
                <form:form method="POST" action="/e-shop/orders/${order.id}/change-manager">
                <td><c:out value="${order.id}"/></td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.product.title}"/></td>
                <td><c:out value="${order.product.price}"/></td>
                <td><c:out value="${order.product.discount}"/></td>
                <td><c:out value="${order.processStatus}"/></td>
                <td>
                    <c:if test="${order.processStatus eq 'ADDED' || order.processStatus eq 'PROCESSED'}">
                        <select class="form-control" id="dropDownList" name="oper">
                            <option value="0">select option</option>
                            <c:forEach var="oper" items="${operations}">
                                <option><c:out value="${oper}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </c:if>
                </td>
                <td>
                    <div>
                        <c:if test="${order.processStatus eq 'ADDED' || order.processStatus eq 'PROCESSED'}">
                            <input type="submit" value="Update" class="btn btn-ms btn-primary "/>
                        </c:if>
                    </div>
                </td>
                </form:form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="container">
    <p> Page ${currentPage} from ${pageLikedProducts.totalPages}  </p>
    <br>
    <h6>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item"><a class="page-link"
                                             href="/e-shop/orders/manager?sort=${sort}&page=${currentPage - 1}&size=5">Previous</a>
                    </li>
                </c:if>
                <c:if test="${currentPage < pageLikedProducts.totalPages}">
                    <li class="page-item"><a class="page-link"
                                             href="/e-shop/orders/manager?sort=${sort}&page=${currentPage + 1}&size=5">Next</a></li>
                </c:if>
            </ul>
        </nav>
    </h6>
</div>
</body>
</html>