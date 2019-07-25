
$(window).scroll(function()
{  
 var mainNavbar = $('.navbar');
 //leftBoxElements.css( 'background-color', '#e0f0e0' );
 mainNavbar.css( 'top', $(window).scrollTop());
 console.log( mainNavbar.css('position'));
});

$(function($){
  //вызывается после загрузки страницы
  $("#search-phone").mask("8(999) 999-99-99");
  //нахождение элемента по его типу
  $('button[type="submit"]').prop('disabled', true); 
});


function changeFildFormSearch(){
    if ($("#search-phone").val() ==="" && $("#search-fio").val() ===""){
       $('button[type="submit"]').prop('disabled', true);
    }
    else{
       $('button[type="submit"]').prop('disabled', false);
    }
}

function changeFildFormNew(){
    if ($("#new-phone").val() ==="" || $("#new-fio").val() ===""){
       $('.btn_new').prop('disabled', true);
    }
    else{
       $('.btn_new').prop('disabled', false);
    }
}

function choiceFile(){
   $('#inputFile').trigger('click');
  } 

function changeFile(event){
  var tmppath = URL.createObjectURL(event.target.files[0]); 
  $("#choiceFile").attr('src',tmppath); 
   //$("#choiceFile").attr('src',event.target.files[0].name);  
 }

$("#vkLogin").on("click", function (e) {
   window.location.replace("https://oauth.vk.com/authorize?client_id=7022245&display=popup&redirect_uri=http://localhost:8080/Phonebook/index.html&scope=friends,status,offline&response_type=code&v=5.92");
   return false;
 });   

$("#fbLogin").on("click", function (e) {
   FB.init({
      appId : '2306939309357325',
      cookie : true, 
      xfbml : true, // parse social plugins on this page
      version : 'v2.8' // use graph api version 2.8
   });

   FB.login(function(response) {
       if (response.status === 'connected') {
          var xhr = new XMLHttpRequest();//выполняет запрос на сервер синхронный или асинхронный
          xhr.open('POST', 'http://localhost:8080/Phonebook/FbServlet', false);//передаем через post зашишенный ключ
          const formData = new FormData();
          formData.append("accessToken", response.authResponse.accessToken);
          xhr.send(formData);
          $("#container_data").html(xhr.responseText);
       }else{
          alert('Ошибка подключения ' + response.status);
       }
   }, {scope: 'user_photos,user_friends'}); 
 
  
   return false;
 });  

$("#search-fio").on("change", changeFildFormSearch);
$("#search-phone").on("change", changeFildFormSearch);

$(".request_DB").on("click", function (e) {
    
    var xhr = new XMLHttpRequest();//выполняет запрос на сервер синхронный или асинхронный
    xhr.open('POST', 'http://localhost:8080/Phonebook/index.html', false);
    const formData = new FormData();
    if (this.toString()==="[object HTMLButtonElement]"){
        formData.append("home", "false");
        formData.append("fio", document.getElementById("search-fio").value);
        formData.append("phone", document.getElementById("search-phone").value);
    }else{
        formData.append("home", "true");
    }     
    xhr.send(formData);
    $("#container_data").html(xhr.responseText);
    return false; // если true то будет переход
});


$("#a_home").on("click", function (e) {
  let content = "<div class='row content_row justify-content-center'> <div class = 'col-8 new_contact'> <div class='col-12 new_contact_title'> \
  <div class='col-12 new_contact_title_body'> Новый контакт </div> </div> <div class='col-12 new_contact_body'> \
  <div class='promt_div hidden_element'  id='promt_div'> Уже существует пользователь с такими данными в базе </div> \
  <img src='img/new foto.png' class='img-fluid new_contact_body_img' id = 'choiceFile'>  <input type='file' id='inputFile' style='display: none;'/>\
  <div class='content_div'> <div class='input-group mr-2'> \
  <button class='btn btn-success btn-cursor' type='button'> <i class='fa fa-user'></i> </button> \
  <input class='form-control py-2' type='search' placeholder='ФИО' id='new-fio'> </div> <div class='input-group mr-2 div_margin_top' > \
  <button class='btn btn-success btn-cursor' type='button'> <i class='fa fa-phone'></i> </button> \
  <input class='form-control py-2' type='search' placeholder='Телефон' id='new-phone'> </div> </div> </div> \
  <div class='col-12 new_contact_footer'> <button class='new_contact_footer_btn btn_new'> <i class='fa fa-play-circle new_contact_footer_btn_play'> \
  </i> Создать </button> </div> </div> </div>";
  $("#container_data").html(content);
  $("#new-phone").mask("8(999) 999-99-99");
  $('.btn_new').prop('disabled', true);
  $("#new-fio").on("change", changeFildFormNew);
  $("#new-phone").on("change", changeFildFormNew);
  $(".btn_new").on("click", function (e) {
            
   var xhr = new XMLHttpRequest();//выполняет асинхронный запрос на сервер
   //передаем все нужные параметры создаем запись и возврашаем ответ
   //false запрос производится синхронно, если true – асинхронно.
   xhr.open('POST', 'http://localhost:8080/Phonebook/NewCreateUser', false);
   // xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); //если заголовок не указать то исользуется
   // multipart/form-data на сервере в сервлете должна стоять аннотация MultipartConfig параметры передаются через FormData. 
   // Если указать заголовок application/x-www-form-urlencoded параметры можно передать через xhr.send("lorem=ipsum") - 
   // и аннотация MultipartConfig  на сервере не нужна.
   const formData = new FormData();
   formData.append($("#inputFile").prop('files')[0].name, $("#inputFile").prop('files')[0]);
   formData.append("fio", document.getElementById("new-fio").value);
   formData.append("phone", document.getElementById("new-phone").value);
   xhr.send(formData);
   $("#promt_div").html(xhr.responseText);
   document.getElementById('promt_div').classList.remove('hidden_element');
   setTimeout("document.getElementById('promt_div').classList.add('hidden_element')", 3000);
   return false; //return true;
  }); 

  $("#choiceFile").on("click", choiceFile);
  $("#inputFile").on("change", changeFile);

  return false; //return true;
}); 





 

