/*!
    * Start Bootstrap - SB Admin v7.0.4 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2021 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
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

});

$(document).ready(()=>{
    $.ajax({ url: "http://codeforces.com/api/contest.list?gym=false",
        data: { },
    })
    .done((json)=> {
        var tb=$('tbody');
        json.result.forEach( (element) => {
            var contestHtml="<tr>";
            contestHtml+=`<td><a  href="https://codeforces.com/contest/${element.id}" >`+String(element.id)+"</a></td>";
            contestHtml+="<td>"+element.name+"</td>";
            contestHtml+="</tr>";
            tb.append(contestHtml);
        });
        const datatablesSimple = document.getElementById('datatablesSimple');
       if (datatablesSimple) {
           new simpleDatatables.DataTable(datatablesSimple);
       }
    });
});