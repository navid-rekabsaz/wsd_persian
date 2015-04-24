<html>
<head><title>Hello World</title></head>
<body>
Hello World!<br/>

<%! String[] params; %>
<center>You have selected: 

<%
params = request.getParameterValues("select2");
if (params != null) 
   {
      for (int i = 0; i < params.length; i++) 
      {
         out.println ("<b>"+params[i]+"<b>");
      }
   }
   else out.println ("<b>none<b>");
%>
</body>
</html>


