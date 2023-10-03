<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Buysell</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Панель администратора</h4>
<table>
    <tr>
        <th>Email</th>
        <th>Номер телефона</th>
        <th>Активность</th>
        <th>Роли</th>
        <th>Бан пользователя</th>
        <th>Изменение роли пользователя</th>
        <th>Подробная информация</th>
    </tr>
    <#list users as user>
        <tr>
            <th>${user.email}</th>
            <th>${user.phoneNumber}</th>
            <th>${user.active}</th>
            <th><#list user.roles as role><${role}<#sep>, </#list></th>
            <th>
                <form action="admin/user/ban/${user.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" name="Бан">
                </form>
            </th>
            <th><a href="admin/user/edit/${user.id}">Редактирование</a> </th>
            <th><a href="/user/${user.id}">Подробная информация</a></th>
        </tr>
    <#else>
        <h3>Пользователей нет</h3>
    </#list>
</table>

</body>
</html>