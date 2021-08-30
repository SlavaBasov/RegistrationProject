<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.08.2021
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <header class="d-flex flex-wrap justify-content-start py-3 mb-4 border-bottom">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="../index.jsp" class="nav-link">Главная</a></li>
            <li class="nav-item"><a href="#" class="nav-link">Вход</a></li>
            <li class="nav-item"><a href="#" class="nav-link  active" aria-current="page">Регистрация</a></li>
        </ul>
    </header>
</div>

<div class="container-fluid">
    <form action="/registration-servlet" method="post">
        <div class="row">
            <label class="form-label col-2" for="input_login">Введите логин:</label>
            <div class="col-3">
                <input class="form-control" name="login" type="text" id="input_login">
            </div>
        </div>
        <div class="row">
            <label class="form-label col-2" for="input_password">Введите пароль</label>
            <div class="col-3">
                <input class="form-control" name="password" type="text" id="input_password">
            </div>
        </div>
        <div class="row">
            <label class="form-label col-2" for="input_password2">Повторите пароль</label>
            <div class="col-3">
                <input class="form-control" name="password2" type="text" id="input_password2">
            </div>
        </div>
        <div class="row">
            <label class="form-label col-2" for="input_mail">Почта</label>
            <div class="col-3">
                <input class="form-control" name="mail" type="text" id="input_mail">
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Submit</button>
    </form>
</div>
</body>
</html>
