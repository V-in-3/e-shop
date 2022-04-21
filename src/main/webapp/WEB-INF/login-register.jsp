<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
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
        <div style="text-align: center;">
            <h1>
                BE KIND, REGISTER OR LOGIN
            </h1>
        </div>
    </div>
        <table class="table table-striped ">
            <thead class="thead-dark">
            <tr>
                <th scope="col"><a href="/e-shop/?sort=title">Register</a></th>
                <th scope="col"><a href="/e-shop/?sort=price">Login</a></th>
            </tr>
            </thead>
            <tbody>
                <tr scope="row">
                    <td>
                        <form:form class="user-form" action="/e-shop/" method="post" modelAttribute="registration">
                            <div class="form-group">
                                <form:label path="name">Name</form:label>
                                <form:errors path="name"/>
                                <form:input class="form-control" path="name"/>
                            </div>
                            <div class="form-group">
                                <form:label path="email">Email</form:label>
                                <form:errors path="email" cssStyle="color: red"/>
                                <form:input type="email" class="form-control" path="email"/>
                            </div>
                            <div class="form-group">
                                <form:label path="password">Password</form:label>
                                <form:errors path="password" cssStyle="color: red"/>
                                <form:password class="form-control" path="password"/>
                            </div>
                            <div class="form-group">
                                <form:label path="passwordConfirmation">Confirm Password</form:label>
                                <form:errors path="passwordConfirmation" cssStyle="color: red"/>
                                <form:password class="form-control" path="passwordConfirmation"/>
                            </div>
                            <button>Register</button>
                        </form:form>
                    </td>
                    <td>
                        <form action="/e-shop/login" class="user-form" method="post">
                            <span style="color:red">${ error }</span>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" name="email" id="email" class="form-control"/>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password" class="form-control"/>
                            </div>
                            <button>Login</button>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>