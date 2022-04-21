<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta charset="ISO-8859-1">
    <title>New Product</title>
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
    padding-top: 100px;">
    <h1 class="h3 mb-3 font-weight-normal">New Product</h1>
    <p>
        <form:errors path="product.*"/>
    </p>
    <form:form method="POST" action="/e-shop/products/new" modelAttribute="product">
        <p>
            <form:label path="title">Title:</form:label>
            <form:input type="title" path="title" class="form-control"/>
            <form:label path="price">Price:</form:label>
            <form:input type="price" path="price" class="form-control"/>
            <form:label path="discount">Discount:</form:label>
            <form:input type="discount" path="discount" class="form-control"/>
        </p>
        <input type="submit" value="Create" class="btn btn-lg btn-primary "/>
    </form:form>
</div>
</div>
</body>
</html>