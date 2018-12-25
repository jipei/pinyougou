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
if条件控制语句<br>

<#assign bool=true/>
<#if bool>
    bool的值为true；
<#else>
    bool的值为false；
</#if>

<br>
<hr>
<br>
list循环控制语句<br>
<#list goodsList as goods>
    ${goods_index} -- ${goods.name} --- ${goods.price}<br>
</#list>


<br>
<hr>
<br>


</body>
</html>