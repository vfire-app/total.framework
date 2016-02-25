<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>测试页面</title>
<style type="text/css">
body>div {
	border: 1pt #F00 solid;
	margin: 10px 10px;
	padding: 10px 10px;
}

h1 {
	font-size: 20pt;
}

form label {
	margin: 5px;
}

form input {
	width: 50%;
	margin: 5px;
}

form button {
	margin: 5px;
}
</style>
</head>
<body>
    <div>
        <h1>测试spring mvc validator，使用hibernate-validator的校验。</h1>
        <div>/vali1</div>
        <form action="/vali1" method="post">
            <label>username:</label><input name="username" type="text" value="xiaoming" /><br /> <label>password:</label><input name="password" type="text"
                value="000000"
            /><br /> <label>numbcode:</label><input name="numbcode" type="text" value="3721" /><br />
            <button type="submit">测试提交</button>
        </form>
    </div>

    <div>
        <h1>测试spring mvc validator，使用hibernate-validator的校验。</h1>
        <div>/vali2</div>
        <form action="/vali2" method="post">
            <label>username:</label><input name="username" type="text" value="xiaoming" /><br /> <label>password:</label><input name="password" type="text"
                value="000000"
            /><br /> <label>numbcode:</label><input name="numbcode" type="text" value="3721" /><br /> <label>a:</label><input name="a" type="text" value="a" /><br />
            <button type="submit">测试提交</button>
        </form>
    </div>

    <div>
        <h1>测试spring mvc validator，使用hibernate-validator的校验。</h1>
        <div>/vali3</div>
        <form action="/vali3" method="post">
            <label>username:</label><input name="username" type="text" value="" /><br /> <label>password:</label><input name="password" type="text"
                value=""
            /><br /> <label>numbcode:</label><input name="" type="text" value="3721" /><br /> <label>a:</label><input name="a" type="text" value="a" /><br />
            <button type="submit">测试提交</button>
        </form>
    </div>
</body>
</html>