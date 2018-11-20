function login() {
	
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	console.log(password);
	console.log(username);
	var md5pwd = hex_md5(password);
	var param = {
			'username' : username,
			'password' : md5pwd,
		};
	
	$.ajax({
        cache: true,
        type: 'POST',
        url: 'checkLogin',
        data: param,
        async: true,
        error: function () {
            console.log('ajax error');
        },
        success: function (res) {
        	var msg = res['msg'];
        	console.log(msg);
        	if(msg === 'success') {
        		window.location.href = 'main.html';
			}else{
        		console.log('账号密码错误');
        		alert('账号或密码错误');
			}
        }
    });

};

$(document).keyup(function(event){
	 if(event.keyCode ==13){
	  $("#login_btn").trigger("click");
	 }
	});