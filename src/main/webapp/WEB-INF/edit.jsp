<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta charset="ISO-8859-1">
    <title>Edit<c:out value="${product.title}"></c:out></title>
</head>
<body>
<div class="container">
    <div style=" margin: auto;
    text-align: right;
    padding-top: 20px; padding-bottom: 30px;">
        <a href="/e-shop/products" class="btn btn-sm btn-primary">Back</a>
        <a href="/e-shop/logout" class="btn btn-sm btn-primary">Logout</a>
    </div>
    <div style="    margin: 0px auto;
    width: 1200px;
    text-align: center;
    padding-top: 100px;">
        <h1 class="mb-3 font-weight-normal">
            Edit
            <c:out value="${product.title}"/>
        </h1>

        <p>
            <form:errors path="product.*"/>
        </p>

        <table class="table table-striped ">
            <thead class="thead-dark">
            </thead>
            <tbody>
            <tr scope="row">
                <td>
                    <form:form method="POST" action="/e-shop/products/${product.id}/edit"
                               modelAttribute="product">
                        <p>
                            <form:label path="title">Title:</form:label>
                            <form:input type="title" path="title" class="form-control"/>
                        </p>
                        <p>
                            <form:label path="title">Price:</form:label>
                            <form:input type="price" path="price" class="form-control"/>
                        </p>
                        <input type="submit" value="Update" class="btn btn-lg btn-primary "/> <br><br>
                    </form:form></td>
            </tr>
            </tbody>
        </table>
        <table class="table table-striped ">
            <thead class="thead-dark">
            </thead>
            <tbody>
            <tr scope="row">
                <td>
                    <form:form method="POST" action="/e-shop/products/${product.id}/delete">
                        <input type="submit" value="Delete" class="btn btn-lg btn-primary"/></form:form>
                </td>

            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>