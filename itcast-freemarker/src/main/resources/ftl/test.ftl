<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker测试</title>
</head>
<body>
${name}---${msg}
<br>
<hr>
<br>
<#-- Freemarker指令学习 -->
assign指定变量<br>
<#assign str="Merry Christmas to JBL. i_am_ljb 欢迎勾搭！"/>
${str}
<br>
<#assign info={"address":"吉山村","mobile":"13400000000"} />
${info.address} --- ${info.mobile}
<br>
<hr>
<br>
include包含其他模版文件<br>
<#include "header.ftl"/>

<br>
<hr>
<br>

<br>
<hr>
<br>

<br>
<hr>
<br>
</body>
</html>