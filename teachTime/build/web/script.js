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
                var subjects = [response.length];
                for(i;i<response.length;i++){         
                    $("#select_subject").append("<option value="+response[i].key+">"+response[i].nome+"</option>");
                }
              }
        });   
    }
});


$("#select_category_insert").on("change", function(){
    $("#select_subject_insert option:not([value='']").remove();
    if(this.value!=''){
        $.ajax({
              datatype:'json',
              type: 'get',
              url: 'http://localhost:8080/teachTime/MainApplication/rest/categories/'+this.value+'/subjects',
              success: function(response) {
                var i = 0;
                for(i;i<response.length;i++){
                    
                    $("#select_subject_insert").append("<option value="+response[i].key+">"+response[i].nome+"</option>");
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


function getRipetizioniLogged(token){
    $(".privateLessons").empty();
    $.ajax({
          datatype:'json',
          type: 'get',
          url: 'http://localhost:8080/teachTime/MainApplication/rest/auth/'+token+'/privateLessons',
          data: {
           city:$("#city").val(),
           category:$("#select_category").val(),
           subject:$("#select_subject").val()
          },
          success: function(response) {
            var i = 0;           
            for(i;i<response.length;i++){
                avg(response[i].tutor.key, function(output, tutor_id){
                    if(output==""){
                        output="0";
                    }
                    if($(".star_tutorid"+tutor_id).length == 0 ){
                        $("."+tutor_id).before("<spam class='star_tutorid"+tutor_id+"'>"+parseFloat(output.substring(0,3))+"</spam>");
                    }
                });
                $("#title_pl").append("<section class='privateLessons'><header aria-controls='contentA"+(i+1)+"' aria-expanded='true'>\n\
                                <h2>"+capitalize(response[i].tutor.nome)+" "+capitalize(response[i].tutor.cognome[0])+". &nbsp; \n\
                                ("+age(response[i].tutor.dataDiNascita)+" anni) &nbsp <i class='fa fa-star "+response[i].tutor.key+"'>\n\
                                </i></h2></header><div id='contentA"+(i+1)+
                                "'aria-hidden='false'><p><b>Titolo di Studi:</b> "+response[i].tutor.titoloDiStudi+"</p><p class='costo'>"+response[i].costo+"€/h</p><p><b>Città:</b> "+
                                response[i].città+"</p><p><b>Luogo di incontro:</b> "+
                                response[i].luogoIncontro+"</p><p id='appendhere"+i+"'><b>Materie:</b> </p><p> <b> Descrizione: </b>"+
                                response[i].descr+"</p></div></section>");
                if(response[i].tutor.imgProfilo!=="" && response[i].tutor.imgProfilo!=null){
                    $("#contentA"+(i+1)).prepend("<img src='fotoProfilo/"+response[i].tutor.imgProfilo+"' class='img-circle'>");
                }
                var k = 0;
                for(k;k<response[i].materie.length;k++){
                    if(k==response[i].materie.length-1){
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome); 
                    }else{
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome+","); 
                    }
                }
                /*$(".accordion").each(function(){ 
                    makeAccordion(this);
                });*/
            }
          }
    });
    
}


$("#filter").on("click",function(){
    var token = localStorage.getItem('myToken');
    if(token != null && token!="" && token != undefined){
        //se l'utente è loggato restituiamo solo le ripetizioni delle quali non è tutor
        getRipetizioniLogged(token);
    }else{
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
                    if(output==""){
                        output="0";
                    }
                    if($(".star_tutorid"+tutor_id).length == 0 ){
                        $("."+tutor_id).before("<spam class='star_tutorid"+tutor_id+"'>"+parseFloat(output.substring(0,3))+"</spam>");
                    }
                });
                $("#title_pl").append("<section class='privateLessons'><header aria-controls='contentA"+(i+1)+"' aria-expanded='true'>\n\
                                <h2>"+capitalize(response[i].tutor.nome)+" "+capitalize(response[i].tutor.cognome[0])+". &nbsp; \n\
                                ("+age(response[i].tutor.dataDiNascita)+" anni) &nbsp <i class='fa fa-star "+response[i].tutor.key+"'>\n\
                                </i></h2></header><div id='contentA"+(i+1)+
                                "'aria-hidden='false'><p><b>Titolo di Studi:</b> "+response[i].tutor.titoloDiStudi+"</p><p class='costo'>"+response[i].costo+"€/h</p><p><b>Città:</b> "+
                                response[i].città+"</p><p><b>Luogo di incontro:</b> "+
                                response[i].luogoIncontro+"</p><p id='appendhere"+i+"'><b>Materie:</b> </p><p> <b> Descrizione: </b>"+
                                response[i].descr+"</div></section>");
                if(response[i].tutor.imgProfilo!=="" && response[i].tutor.imgProfilo!=null){
                    $("#contentA"+(i+1)).prepend("<img src='fotoProfilo/"+response[i].tutor.imgProfilo+"' class='img-circle'>");
                }
                var k = 0;
                for(k;k<response[i].materie.length;k++){
                    if(k==response[i].materie.length-1){
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome); 
                    }else{
                        $("#appendhere"+i).append(" "+response[i].materie[k].nome+","); 
                    }
                }
                /*$(".accordion").each(function(){ 
                    makeAccordion(this);
                });*/
            }
          }
    });
}
//$('body,html').animate({ scrollTop: $('body').height() }, 800);
});

/*
function makeCollapsible(container)  {
    var colcontainer = $(container);
    colcontainer.addClass("enabled");
    var colheader = $("header",colcontainer);
    var colbodyid = colheader.attr("aria-controls");							
    var colbody = $("#"+colbodyid,colcontainer);			
    var colindicator = $("<div class='indicator'><i class='fa'></i></div>");
    colheader.append(colindicator);
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
    });
    
    //init
    if (colheader.attr("aria-expanded")=="true") {					
        colcontainer.trigger("awdcoll:expand",true);				
    } else {	
        colcontainer.trigger("awdcoll:collapse",true);									
    }	
}


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

*/
$(function() {
    $("#select_category_insert").val("");
    $("#select_category").val("");
    $("#nuova_materia").val("");
    $("#select_subject_insert").val("");
    $("#select_subject").val("");
    $("#nuova_materia").on("keyup", function(){
        $("#select_subject_insert").val("");
    });
    //jQuery.fn.extend({awdColl: function(){return this.each(function(){makeCollapsible(this)});}});
    //$(".collapsible.v0:first").awdColl();
    /*$(".accordion").each(function(){ 
        makeAccordion(this);
    });*/	
    makeLogin($("#login-trigger"));
    loginBtn($("#login_btn"));
    checkLogin();
    
});


function makeLogin(login){
    login.on("click",function() {
        $(this).next('#login-content').slideToggle();
        $(this).toggleClass('active');                    
        if ($(this).hasClass('active')){
            $(this).find('span').html('&#x25B2;');
        }else{ 
            $(this).find('span').html('&#x25BC;');
        }
    });
}



function loginBtn(loginBtn){
   loginBtn.on("click", function(){
   var email = $("#email").val();
   var pwd = $("#password").val();
   if(email.indexOf("@")!= -1){
       $.ajax({
        contentType: "application/json",
        type:'post',
        data:JSON.stringify({
            email: email,
            pwd: pwd
        }),
        url:'http://localhost:8080/teachTime/MainApplication/rest/auth/login',
        success: function(response) {
           
           if(response!=0){
               localStorage['myToken'] = response;
               logoutNav();
               checkLogin();
               $(".privateLessons").empty();
           }  
           
        }
      });
   }   
});
}


function logoutNav(){
    $("#nav_option").empty();
    $("#nav_option").append("<li id='logout'><a id='logout-trigger' href='#'>Logout \n\
                            </a><i class='fa fa-sign-out'></i></li>");
    makeLogout($("#logout-trigger"));
}

function loginNav(){
    $("#nav_option").empty();
    $("#nav_option").append("<li id='login'><a id='login-trigger' href='#'> \n\
                    Login <span>&#x25BC;</span></a><div id='login-content'><div><input id='email'\n\
                    type='email' name='Email' placeholder='Your email address' required><input \n\
                    id='password' type='password' name='Password' placeholder='Password' required><input type='submit' \n\
                    id='login_btn' value='Login'></div></div></li>");
    makeLogin($("#login-trigger"));
    loginBtn($("#login_btn"));
}


function makeLogout(logout){
    logout.on("click", function(){     
    var myToken = localStorage.getItem('myToken');
    $.ajax({
            contentType: "text/plain",
            dataType:"text",
            type:'post',
            data:myToken,
            url:'http://localhost:8080/teachTime/MainApplication/rest/auth/logout',
            success: function(response) {
                localStorage.removeItem('myToken');
                $(".privateLessons").empty();
                loginNav();
                checkLogin();
            }
        });
    });
 }
 
 
 function checkLogin(){
     var myToken = localStorage.getItem('myToken');
     if(myToken!=null && myToken!=""){
         $("#insert").removeClass("hidden");
         logoutNav();
     }else{
         $("#insert").addClass("hidden");
         loginNav();
     }
 }
 
 
 $("#add_subject_btn").on("click",function(){
    var category = $("#select_category_insert option:selected");
    var new_subject = $("#nuova_materia").val();
    var subject = $("#select_subject_insert option:selected");
    var input = $("#categorysubjects").val();
    if(subject.val()!=''){
        
        $("#riepilogo").removeClass("hidden");
        if($("#selected_category").length == 0){
            $("#riepilogo_materie").after("<p id='selected_category'><b>Categoria:</b> "+category.text()+"</p>\n\
                                             <p id='selected_subjects'><b>Materie:</b> "+subject.text()+"</p>");
            $("#select_category_insert").attr("disabled","disabled");
            subject.attr("disabled","disabled");
            $("#categorysubjects").val(category.val()+";"+$.trim(subject.text()));
        }else{
            $("#selected_subjects").append(", "+$.trim(subject.text()));
             subject.attr("disabled","disabled");
             $("#categorysubjects").val(input+","+$.trim(subject.text()));
        }
        $("#select_subject_insert").val("");
    }else{
        if(new_subject!=''){
            $("#riepilogo").removeClass("hidden");
            if($("#selected_category").length == 0){
                $("#riepilogo_materie").after("<p id='selected_category'><b>Categoria:</b> "+category.text()+"</p>\n\
                                                 <p id='selected_subjects'><b>Materie:</b> "+new_subject+"</p>");
                $("#select_category_insert").attr("disabled","disabled");
                
                $("#categorysubjects").val(category.val()+";"+new_subject);
            }else{
                $("#selected_subjects").append(", "+new_subject);
                 
                 $("#categorysubjects").val(input+","+new_subject);
            }
            $("#nuova_materia").val("");
        }
    }
 });
 
 
 $("#reset").on("click", function(){
     $("#select_subject_insert").val("");
     $("#select_category_insert").val("");
     $("#selected_category").empty();
     $("#selected_category").remove();
     $("#selected_subjects").empty();
     //$("#riepilogo").css({'display':'none'});
     $("#riepilogo").addClass("hidden");
     $("#select_category_insert").prop("disabled",false);
     $("#select_subject_insert option").each(function(){
         $(this).prop("disabled",false);
     });
     $("#categorysubjects").val("");
     
 });
 
 function resetInsert(){
      $("#reset").trigger("click");  
      $("select").val("");
      $("#città").val("");
      $("#luogoIncontro").val("");
      $("#costo").val("");
      $("#descr").val("");
      $("#nuova_materia").val("");
      $("#insert").scrollTop(0);
 }
 
     
 function msg_ok(){
     $("#msg").removeClass("hidden");
     $("#msg").removeClass("msg_ko");
     $("#msg").addClass("msg_ok");
     $("#msg").text("La ripetizione è stata inserita con successo!");
     setTimeout( function(){$("#msg").addClass("hidden");} , 4000);
 }
     
     
 function msg_ko(){
     $("#msg").removeClass("hidden");
     $("#msg").removeClass("msg_ok");
     $("#msg").addClass("msg_ko");
     $("#msg").text("Qualcosa è andato storto..");
     setTimeout( function(){$("#msg").addClass("hidden");} , 4000);
 }
 
 $("#insert_btn").on("click", function(){
     var myToken = localStorage.getItem('myToken');
     var categorysubjects = $("#categorysubjects").val();
     console.log(categorysubjects);
     if(categorysubjects.indexOf(";") != -1){
            var category_key = parseInt(categorysubjects.split(";")[0]);
            var subjects = categorysubjects.split(";")[1].split(",");
            var i = 0;
            var subjects_list = [];
            for(i;i<subjects.length;i++){
                subjects_list[i]={"nome":subjects[i]};
                console.log(subjects_list);
            }
            var città = $("#città").val();
            var luogoIncontro = $("#luogoIncontro").val();
            var costo = parseInt($("#costo").val());
            var descr = $("#descr").val();
            if(città!="" && luogoIncontro!="" && costo!=""){
                $.ajax({
                      contentType: "application/json",
                      dataType:"json",
                      type:'post',
                      data:JSON.stringify({
                          città:città,
                          luogoIncontro:luogoIncontro,
                          costo:costo,
                          descr:descr,
                          categoria_key:category_key,
                          materie:subjects_list
                      }),
                      url:'http://localhost:8080/teachTime/MainApplication/rest/auth/'+myToken+'/privateLessons',
                      success: function(response) {
                      }
                  });
               resetInsert();
               msg_ok();
           }else{
               msg_ko();
           }
        }else{
            msg_ko();
           }
 });