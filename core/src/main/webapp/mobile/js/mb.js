
    var logged = false;
    
    function setLogged(){
  	    logged = true;
  	    if (typeof(isLogin) != "undefined")
  	       isLogin = true;
  	 }

	function Login(){
	       if (document.getElementById('j_username') == null){
	            return true;
	       }
		   var pars  = "j_username=" + document.getElementById('j_username').value + "&j_password=" + document.getElementById('j_password').value
		               + "&rememberMe=" + document.getElementById('rememberMe').value;
		   var url = loggedURL + "?" + pars;
		   load(url, function(xhr) {				 
				 if (xhr.responseText.indexOf("if(setLogged)setLogged()")   >=0){
					setLogged();
					document.getElementById("messageReply").submit();
				 }else{
				    delloginCookies();
					alert("用户名或密码错误");					
				}
			});
			if(logged) return true;
			else
			  return false;
	}
	
	function delloginCookies(){  	    
    	eraseCookie("rememberMe");
  	  	eraseCookie("username");
   		eraseCookie("password");   		
  	}	
	
	
function leftRightgoPageREST(event)
{
   var page;
   event = event ? event : (window.event ? window.event : null);

   if (event.keyCode==39) 
      page = start + count               
   else if (event.keyCode==37) 
      page = start - count
   else return;      
   
   if (page < 0 || page >= allCount) return;
   
   var path;
    if (page != 0)	    	
	   path = pageURL + "/" + page;
	else
	  path = pageURL ;
	document.location = path;
	
} 
	
	function checkmsg(url){
	   if( readCookie("newMessageW") == "disable")
             return;             
	    load(url, function(xhr) {	   
	             
				 document.getElementById("isNewMessage").innerHTML = xhr.responseText ;
			});
	}
	
    
	function load(url, callback) {

		var xhr;
		
		if(typeof XMLHttpRequest !== 'undefined') xhr = new XMLHttpRequest();
		else {
			var versions = ["Microsoft.XmlHttp", 
			 				"MSXML2.XmlHttp",
			 			    "MSXML2.XmlHttp.3.0", 
			 			    "MSXML2.XmlHttp.4.0",
			 			    "MSXML2.XmlHttp.5.0"];
			 
			 for(var i = 0, len = versions.length; i < len; i++) {
			 	try {
			 		xhr = new ActiveXObject(versions[i]);
			 		break;
			 	}
			 	catch(e){}
			 } // end for
		}
		
		xhr.onreadystatechange = ensureReadiness;
		
		
		function ensureReadiness() {
			if(xhr.readyState < 4) {
				return;
			}
			
			if(xhr.status !== 200) {
				return;
			}

			// all is well	
			if(xhr.readyState === 4) {
				callback(xhr);
			}			
		}
		
		xhr.open('GET', url, true);
		xhr.send('');
	
	}
	
	

function createCookie(name,value,seconds) {
	var date = new Date();
	if (seconds) 
		date.setTime(date.getTime()+(seconds*1000));
	else
		date.setTime(date.getTime()-10000);

	var expires = "; expires="+date.toGMTString();
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function disablePopUPWithID(ID,seconds){
    disablePopUP(seconds);
}

function disablePopUP(seconds){
      createCookie("newMessageW","disable",seconds);
}


function eraseCookie(name) {
	createCookie(name,"",-1);
}