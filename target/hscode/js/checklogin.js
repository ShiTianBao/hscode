function getCookie(name)      
    {
       var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr != null) return true; return false;
    }
    if(getCookie('tdUser')) {
    	window.location.href = 'main.html';
    }