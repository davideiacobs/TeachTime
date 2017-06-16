/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("#select_category").on("change", function(){
    
    $("#select_subject option:not([value='']").remove();
    if(this.value!=''){
        $.ajax({
              datatype:'json',
              type: 'get',
              url: 'http://localhost:8080/teachTime/MainApplication/rest/categories/'+this.value+'/subjects',
              success: function(response) {
                var i = 0;
                for(i;i<response.length;i++){
                    console.log(response[i]);
                    $("#select_subject").append("<option value="+response[i].key+">"+response[i].nome+"</option>");
                }
              }
        });   
    }
});

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function age(date){
    date = new Date(date);
    var today = new Date();
    var age = Math.floor((today-date) / (365.25 * 24 * 60 * 60 * 1000));
    return age;
}
   

$("#filter").on("click",function(){
    $.ajax({
          datatype:'json',
          type: 'get',
          url: 'http://localhost:8080/teachTime/MainApplication/rest/privateLessons',
          data: {
           city:$("#city").val(),
           category:$("#select_category").val(),
           subject:$("#select_subject").val()
          },
          success: function(response) {
            var i = 0;
            for(i;i<response.length;i++){
                $("#title_pl").after("<div><p>"+capitalize(response[i].tutor.nome)+" "+capitalize(response[i].tutor.cognome[0])+". ("+age(response[i].tutor.dataDiNascita)+" anni)</p>\n\
                                            <p>"+response[i].costo+"€/h</p></div>");
            }
          }
    });
    
    
});