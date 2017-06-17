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
                console.log(response[i]);
                avg(response[i].tutor.key, function(output, tutor_id){
                    
                    if(output==""){
                        output="0";
                    }
                    $("."+tutor_id).before(parseFloat(output.substring(0,3)));
    
                });
               
                $("#title_pl").append("<section class='privateLessons'><header aria-controls='contentA"+(i+1)+"' aria-expanded='true'>\n\
                                <h2>"+capitalize(response[i].tutor.nome)+" "+capitalize(response[i].tutor.cognome[0])+". &nbsp; \n\
                                ("+age(response[i].tutor.dataDiNascita)+" anni) &nbsp <i class='fa fa-star "+response[i].tutor.key+"'>\n\
                                </i>&nbsp;&nbsp;"+response[i].costo+"€/h</h2></header><div id='contentA"+(i+1)+
                                "' aria-hidden='false'><p><b>Città:</b> "+
                                response[i].città+"</p><p><b>Luogo di incontro:</b> "+
                                response[i].luogoIncontro+"</p><p id='appendhere"+i+"'><b>Materie:</b> </p><p> <b> Descrizione: </b>"+
                                response[i].descr+"</div></section>");
                if(response[i].tutor.imgProfilo!=="" && response[i].tutor.imgProfilo!=null){
                    $("header").after("<img src='fotoProfilo/"+response[i].tutor.imgProfilo+"' class='img-circle'>");
                }
                var k = 0;
                for(k;k<response[i].materie.length;k++){
                    if(k==response[i].materie.length-1){
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome); 
                    }else{
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome+","); 
                    }
                }
                $(".accordion").each(function(){ 
                    makeAccordion(this);
                });
            }
            
          }
    });

});


function makeCollapsible(container)  {
    var colcontainer = $(container);
    colcontainer.addClass("enabled");
    var colheader = $("header",colcontainer);
    var colbodyid = colheader.attr("aria-controls");							
    var colbody = $("#"+colbodyid,colcontainer);			
    var colindicator = $("<div class='indicator'><i class='fa'></i></div>");
    colheader.append(colindicator);
    
    //usiamo degli eventi custom per dotare i collapsible di "metodi"
    //facilmente richiamabili dall'esterno
    colcontainer.on("awdcoll:expand",function(e,init) {
        if (init) colbody.show();
        colbody.slideDown(function() {
            colheader.attr("aria-expanded","true")
            colcontainer.removeClass("collapsed"); 
            colbody.attr("aria-hidden","false");
            colindicator.children("i").addClass("fa-sort-desc");
            colindicator.children("i").removeClass("fa-sort-asc");
            
            
        });				
    });
    
    colcontainer.on("awdcoll:collapse",function(e,init) {
        if (init) colbody.hide(); //se stiamo inizializzando, nascondi subito senza far vedere l'effetto
        colbody.slideUp(function() {
            colheader.attr("aria-expanded","false")
            colcontainer.addClass("collapsed"); 
            colbody.attr("aria-hidden","true");  
            colindicator.children("i").addClass("fa-sort-asc"); 
            colindicator.children("i").removeClass("fa-sort-desc");
           
        });
    });
    
    
    colheader.on("click",function(){ 			
        if (colheader.attr("aria-expanded")=="true") {					
            colcontainer.trigger("awdcoll:collapse");
        } else {					
            colcontainer.trigger("awdcoll:expand");				
        }						
        //RICEVENTE: $(document).on("awdcoll:expand",function(e){console.log(e.target)})
    });
    
    //init
    if (colheader.attr("aria-expanded")=="true") {					
        colcontainer.trigger("awdcoll:expand",true);				
    } else {	
        colcontainer.trigger("awdcoll:collapse",true);									
    }	
}



//crea un accordion rendendo collapsible le sezioni figlio
//e aggiungendo su ciascuna un ulteriore handler che provoca la
//chiusura delle altre sezioni
function makeAccordion(container) {
    var acccontainer = $(container);
    $("> section",acccontainer).each(function() {
        var colcontainer = $(this);
        var colheader = $("header",colcontainer);				
        colcontainer.addClass("collapsible");
        makeCollapsible(this);		
        colheader.on("click",function(){ 			
            $("> section",acccontainer).not(colcontainer).trigger("awdcoll:collapse");					
        });
    });
}


$(function() {
    //agganciamo come estensione di JQeury una funzione che richiama la nostra makeCollapsible su tutti gli elementi selezionati
    //ritorniamo this (restituito a sua volta da thiseach) per permettere la concatenazione dei metodi di JQuery
    jQuery.fn.extend({awdColl: function(){return this.each(function(){makeCollapsible(this)});}});
    
    $(".collapsible.v0:first").awdColl();
    
    $(".accordion").each(function(){ 
        makeAccordion(this);
    });	
    
});