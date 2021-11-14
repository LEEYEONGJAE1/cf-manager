$(document).ready(()=>{

    $("#alert-success").hide();
    $("#alert-danger").hide();

    $("#idalert-success").hide();
    $("#idalert-danger").hide();

    $("#submitbtn").hide();

    var idcheck=0,pwdcheck=0;

    $("#pwdConfirm").keyup(()=>{
        var pwd=$("#pwd").val();
        var conf=$("#pwdConfirm").val();
        if(pwd != "" || conf != ""){
            if(pwd == conf){
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#submitbtn").show();
                pwdcheck=1;
            }
            else{
                $("#alert-success").hide();
                $("#alert-danger").show();
                    $("#submitbtn").hide();

                pwdcheck=0;
            }
        }
    });
    $("#idCheckValid").click(()=>{
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
                    $("#submitbtn").show();
                }
                else{
                    $("#idalert-success").hide();
                    $("#idalert-danger").show();
                }
            }
        });
    });

});