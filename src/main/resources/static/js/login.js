$(document).ready(()=>{

    $("#alert-danger").hide();
    $('#loginbtn').click(()=>{
        console.log($('#inputEmail').val());
        console.log($('#inputPassword').val());
        //로그인 실패시
        // $("#alert-danger").show();
    });
});