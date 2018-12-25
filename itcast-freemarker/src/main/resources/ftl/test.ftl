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
-------------------
共有${goodsList?size}条记录。

<br>
<hr>
<br>
内建函数使用：<br>
eval将json字符串转换为一个对象<br>
<#assign str2="{'id':123,'name':'heima'}"/>
<#assign jsonObj=str2?eval/>
${jsonObj.id} -- ${jsonObj.name}
<br>
<hr>
<br>
日期格式化显示：<br>
.now 表示当前时间${.now}<br>
today的日期：${today?date}<br>
today的时间：${today?time}<br>
today的日期时间：${today?datetime}<br>
today的格式化日期时间：${today?string("yyyy年MM月dd日 HH:mm:ss SSSS")}<br>

长整型的大数据数值直接显示：${number}；如果要原封不动的显示：${number?c}
<br>
<hr>
<br>
空值的处理<br>
emp为空直接什么都不显示的话，那么加!就可以了：${emp!}；如果值为空要显示内容的话；那么!之后加内容即可：${emp!"emp的值为空"}
<br>
<br>
??? 前面两个??表示变量是否存在，如果存在则返回true，否则返回false；第三个?表示函数的调用。<br>

<br>
<#assign bool3=false />
${bool3???string}

<br>
<br>
<#if str3??>
    str3存在
<#else>
    str3不存在
</#if>


<br>
<hr>
<br>


<br>
<hr>
<br>
</body>
</html>