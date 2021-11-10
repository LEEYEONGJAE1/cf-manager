/*!
    * Start Bootstrap - SB Admin v7.0.4 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2021 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
//
    // Toggle the side navigation

$(document).ready(()=>{
    //대회 정보 불러오기
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
   $('#probleminfo').DataTable({
         ordering: false,
         pageLength:5
   });
    $('#bookmarkedproblems').DataTable({
        ordering:false,
        pageLength:5
    });
    $.ajax({ url: "http://codeforces.com/api/contest.list?gym=false",
        data: { },
    })
    .done((json)=> {
       var data=json.result;
       data.forEach((element)=>{ element.id=`<a href="https://codeforces.com/contest/${element.id}" >${String(element.id)}</a>`}); //링크 삽입
       $('#contest').DataTable({
            data:data,
             columns: [
                    { data: 'id' },
                    { data: 'name' },
             ],
             ordering: false,
             pageLength: 5

       });

    });

});