$(document).ready(()=>{

    $("#alert-success").hide();
    $("#alert-danger").hide();

    $("#submitbtn").hide();
    $("input").keyup(()=>{
        var pwd=$("#pwd").val();
        var conf=$("#pwdConfirm").val();
        if(pwd != "" || conf != ""){
            if(pwd == conf){
                $("#alert-success").show();
                $("#alert-danger").hide();
                $("#submitbtn").show();
            }
            else{
                $("#alert-success").hide();
                $("#submitbtn").hide();
                $("#alert-danger").show();
            }
        }
    });

});