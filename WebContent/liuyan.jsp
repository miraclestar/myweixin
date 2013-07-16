<%@ page language="java" import="java.sql.*,java.util.*,info.xmark.base.*" pageEncoding="UTF8"%>
<!-- 

@author hyliu
 用于数据展示。实现了排序、查询、分页
 只需要在页面提交数据库的表名即可
 
 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


  %>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>留言板</title>
    
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">    
  <meta name="viewport" content="width=device-width, initial-scale=0.5, minimum-scale=0.3, maximum-scale=2.0, user-scalable=yes" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" type="text/css" href="css/style.css">
  </head>
  <body width='300px'>
  <%
  Connection conn = DBPool.getInstance().getConnection();
  String sql="select * from liuyan order by dt desc limit 50";
  PreparedStatement pstmt =null;
  ResultSet rs =null;
  
    try{
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while(rs.next()){
    	  %>
    	  	<div class='content'><%=rs.getInt("id")%>:<%=rs.getString("content") %>--<span><%=rs.getTimestamp("dt") %></span></div>
    	  <%
      }
    }catch(Exception e){
    	DBPool.getInstance().close(pstmt, rs, conn);
    }
    
  %>
 </body>
 </html>