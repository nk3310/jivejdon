
<%
String url = (String)request.getSession().getServletContext().getAttribute("HOSTURL");
if (url == null){
	java.net.URL reconstructedURL = new  java.net.URL(request.getScheme(),
            request.getServerName(),
            request.getServerPort(),
            request.getContextPath());
    url = reconstructedURL.toString().replace(":80/", "/");
    request.getSession().getServletContext().setAttribute("HOSTURL", url);
}

%>