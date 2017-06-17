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

function avg(tutor_id, handledata){
    $.ajax({
        datatype:'text',
        type:'get',
        url:'http://localhost:8080/teachTime/MainApplication/rest/users/'+tutor_id+'/feedbacks/avg',
        success: function(response) {
           if(response==""){
               reponse="0";
           }
               
           handledata(response, tutor_id);
            
        },error: function(){
            handledata(0, tutor_id);
        }
    });
}
/*
function avg(tutor_id){
   
    $.ajax({
        datatype:'text',
        type:'get',
        async:false,
        url:'http://localhost:8080/teachTime/MainApplication/rest/users/'+tutor_id+'/feedbacks/avg',
        success: function(response) {
            //console.log(response);
           return response;
            
        }
    });
}
*/
$("#filter").on("click",function(){
    $(".privateLessons").empty();
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
                
                avg(response[i].tutor.key, function(output, tutor_id){
                    console.log(tutor_id);
                    if(output==""){
                        output="0";
                    }
                    $("."+tutor_id).before(parseFloat(output.substring(0,3)));
    
                });
                $("#title_pl").after("<div class='privateLessons'><p>"+capitalize(response[i].tutor.nome)+" "
                                        +capitalize(response[i].tutor.cognome[0])+
                                        ". ("+age(response[i].tutor.dataDiNascita)+
                                        " anni)</p><i class='fa fa-star "+response[i].tutor.key+"'></i>\n\
                                        <p>"+response[i].costo+"€/h</p></div>");
        
            }
            
          }
    });
    
    
});