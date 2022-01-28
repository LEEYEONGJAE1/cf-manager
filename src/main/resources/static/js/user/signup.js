var IdChecked=false;
var PwdChecked=false;
var CodeChecked=true;
var signUpValid=()=>{
    if(IdChecked&&PwdChecked&&CodeChecked){
        $("#submitbtn").show();
    }
    else{
        $("#submitbtn").hide();
    }
}

$(document).ready(()=>{


    $("#codealert-success").hide();
    $("#codealert-danger").hide();

    $("#alert-success").hide();
    $("#alert-danger").hide();

    $("#idalert-success").hide();
    $("#idalert-danger").hide();

    $("#submitbtn").hide();

    $("#checkAdminCode").keyup(()=>{
      var AdminCode=$("#checkAdminCode").val();
      if(AdminCode==="0000"){
        $("#codealert-success").show();
        $("#codealert-danger").hide();
        CodeChecked=true;
      }
      else{
        $("#codealert-success").hide();
        $("#codealert-danger").show();
        Codechecked=false;
      }
      signUpValid();
    });

    $("#pwdConfirm").keyup(()=>{
        var pwd=$("#pwd").val();
        var conf=$("#pwdConfirm").val();
        if(pwd != "" || conf != ""){
            if(pwd == conf){
                $("#alert-success").show();
                $("#alert-danger").hide();
                PwdChecked=true;
            }
            else{
                $("#alert-success").hide();
                $("#alert-danger").show();
                PwdChecked=false;
            }
            signUpValid();
        }
    });
    $("#inputId").keyup(()=>{
        console.log("idchk");
        $.ajax({
            url: "/idcheck",
            type: "post",
            beforeSend: (jqXHR, settings) => {
               var header = $("meta[name='_csrf_header']").attr("content");
               var token = $("meta[name='_csrf']").attr("content");
               console.log(header);
               console.log(token);
               jqXHR.setRequestHeader(header, token);
            },
            data:{
                userid:$("#inputId").val()
            },
            success: (result) => {
                console.log(result);
                if(result==1){
                    $("#idalert-success").show();
                    $("#idalert-danger").hide();
                    IdChecked=true;
                }
                else{
                    $("#idalert-success").hide();
                    $("#idalert-danger").show();
                    IdChecked=false;
                }
                signUpValid();
            }
        });
    });

});