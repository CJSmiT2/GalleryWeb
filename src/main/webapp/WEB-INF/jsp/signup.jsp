<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <jsp:include page="head.jsp" />
        <style>
            html,
            body {
                height: 100%;
            }

            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }

            .form-signin .checkbox {
                font-weight: 400;
            }

            .form-signin .form-floating:focus-within {
                z-index: 2;
            }

            .form-floating{
                margin: 5px 0;
            }
        </style>
    </head>
    <body class="text-center">

        <jsp:include page="top_menu.jsp" />

        <main class="form-signin">
            <form method="post">
                <h1 class="h3 mb-3 fw-normal">Sign Up form</h1>

                <div class="form-floating">
                    <input type="text" name="login" class="form-control" id="floatingInput" placeholder="login">
                    <label for="floatingInput">Login</label>
                </div>
                
                <div class="form-floating">
                    <input type="text" name="nickName" class="form-control" id="floatingInput" placeholder="nickName">
                    <label for="floatingInput">NickName</label>
                </div>
                
                <div class="form-floating">
                    <input type="email" name="email" class="form-control" id="floatingInput" placeholder="Email">
                    <label for="floatingInput">Email</label>
                </div>
                
                <div class="form-floating">
                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                    <label for="floatingPassword">Password</label>
                </div>

                <button class="w-100 btn btn-lg btn-primary" type="submit">Sign Up</button>
            </form>
        </main>


        <jsp:include page="footer.jsp" />
    </body>
</html>
