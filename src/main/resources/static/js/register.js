$(document).ready(()=>{
    $("#alert-success").hide();
    $("#alert-danger").hide();
    var valid=0,pwd,conf;
    $("input").keyup(()=>{
        pwd=$("#inputPassword").val();
        conf=$("#inputPasswordConfirm").val();
        if(pwd != "" || conf != ""){
            if(pwd == conf){
                $("#alert-success").show();
                $("#alert-danger").hide();
                valid=1;
            }
            else{
                $("#alert-success").hide();
                $("#alert-danger").show();
                valid=0;
            }
        }
    });

    $('#registerbtn').click(()=>{
        if(valid){

        }
        else{
            alert('비밀번호를 확인해주세요.');
        }
    });
});
