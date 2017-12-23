/**
 * Created by yorge on 2017/9/20.
 */
var login_number=0;
var verifyCode ;
$(function(){
	  //记住密码功能
    var str = getCookie("logininfo");
    str = str.substring(1,str.length-1);
    var username = str.split(",")[0];
    var password = str.split(",")[1];
    //自动填充用户名和密码
    $("#form_username").val(username);
    $("#form_password").val(password);
    if(str!=''){
    	$("#form_isRememberme").attr("checked",true);
    }
})

function modal_login(){
	if(login_number==0){
	$("#validate_code").hide();
	}else{
	$("#validate_code").show();
	}
}

/*$().ready(function() {
    
 验证码
    var verifyCode = new GVerify("v_container");

    $("#my_button").click(function () {
        var res = verifyCode.validate(document.getElementById("code_input").value);
        if (res) {
        	
        	login();
            如果正确，判断登录的用户类型
             * 如果checkText是1，用户是员工
             * 如果checkText是2，用户是管理员
             * 
            var checkText = $("#el_userType").find("option:selected").val();
            if (checkText == 1) {
                $("#el_form").prop("action","");
                $("#el_form").prop("action","view/lineExam/examInterface.jsp");
            } else {
                $("#el_form").prop("action","");
                $("#el_form").prop("action","view/examParper/examManage.jsp");
                //$("#el_loginA").css("href", "lineExam/examInterface.html")
            }
        } else {
            alert("验证码错误")
            $("#code_input").val('');
        }
    })

});
*/
function Enter_login(){	
		 if (event.keyCode==13)  //回车键的键值为13
		   document.getElementById("my_button").click(); //调用登录按钮的登录事件		
}
function login_error(){
	verifyCode = new GVerify("v_container");
	login_number+=1;
	$("#code_input").val('');
	$("#validate_code").show();
	//登录框的高
	$("#myModal").find(".modal-content").css("height", "500");
}

function login(){
	var res=true;
	if(login_number>0){	
		res = verifyCode.validate(document.getElementById("code_input").value);
	}
	if(res){
		$.ajax({
			url:"/Exam/user_login.action",
			data:$("#el_form").serialize(),
			type :"POST",
			dataType:"json",
			success:function(data){
				var login_result=data.login_result;
				var user_type=data.user_type;
				var login_url;
				if('2'==user_type){
					login_url=data.login_url;	
				}
				switch(login_result){    
					case 'error001':alert("该账号不存在");login_error(); break;
					case 'error002':alert("密码错误");login_error(); break;
					case 'error003':alert("该账号没有任何权限，不能使用该系统，请先分配角色");login_error(); break;
					case 'error':alert("未知错误");login_error(); break;
					case 'success_employee':window.location.href=baseUrlPath+"/view/lineExam/examInterface.jsp";break;
				    case 'success_manager':window.location.href=baseUrlPath+"/view/"+login_url;break;
				}
			}
		})
	}else{
		alert("验证码错误")
        $("#code_input").val('');
	}
}

//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
