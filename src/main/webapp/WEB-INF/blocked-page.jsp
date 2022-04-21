<%--
  Created by IntelliJ IDEA.
  User: Volodymyr
  Date: 18.04.2022
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <meta charset="ISO-8859-1">
    <title>User blocked!!!</title>
</head>
<body>
<div class="container">
    <div style="margin: 0px auto;
    text-align: right;
    padding-top: 20px; padding-bottom: 30px;">
    <table class="table table-striped ">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Information</th>
            <th scope="col"><div style="margin: 0px auto; text-align: right;">Action</div></th>
        </tr>
        </thead>
        <tbody>
        <tr scope="row">
            <td>
                <h1>
                    <span th:if="${error}" th:text="${error}">Error</span>
                </h1>
                <h2>${error}</h2>
            </td>
            <td>
                <div style=" margin: 0px auto; text-align: right; padding-top: 20px; padding-bottom: 30px;">
                    <a href="/e-shop/redirect" class="btn btn-lg btn-primary">Register/Login</a>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</div>
</body>
</html>
