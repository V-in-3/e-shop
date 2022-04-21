<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta charset="ISO-8859-1">
    <title><c:out value="${product.title}"></c:out></title>
</head>
<body>
<div class="container">
<div style=" margin: 0px auto;
    text-align: right;
    padding-top: 20px; padding-bottom: 30px;">
    <a href="/e-shop/products" class="btn btn-sm btn-primary">Back</a>
    <a href="/e-shop/logout" class="btn btn-sm btn-primary">Logout</a>
</div>
<div style="    margin: 0px auto;
    width: 1200px;
    text-align: center;
    padding-top: 100px;
    text-align: left;">
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Title</th>
            <th scope="col">Price</th>
        </tr>
        </thead>
        <tbody>
        <tr scope="row">
            <td>
                <h1>
                    <c:out value="${product.title}"/>
                </h1>
            </td>
            <td>
                <h1>
                    <c:out value="${product.price}"/>
                </h1>
            </td>

        </tr>
        </tbody>
    </table>

    <h2 class="h3 mb-3 font-weight-normal">
        Created By:
        <c:out value="${product.createdBy}"/>
    </h2>
    <h1>Users who liked this product:</h1>
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${product.users}" var="u">
            <tr>
                <td><c:out value="${u.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style=" margin: 0px auto;text-align: center;">
        <c:if test="${user.userRole eq 'MANAGER' || user.userRole eq 'ADMIN'}">
            <a href="/e-shop/products/${product.id}/edit" class="btn btn-lg btn-primary">Edit</a>
        </c:if>
    </div>
</div>
</div>
</body>
</html>