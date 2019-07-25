/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebook.servlets;


import com.mycompany.phonebook.entitys.Users;
import com.mycompany.phonebook.dao.DaoEjb;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.status.Status;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//запрос post можно передавать несколькими способами либо через форму либо js объект XMLHttpRequest
//если через форму ил при использовании js использовать объект FormData то нужна аннотация MultipartConfig
//в js можно использовать заголовок "application/x-www-form-urlencoded" и передавать параметры типа 
//"lorem=ipsum" тогда анотация MultipartConfig не нужна urlPatterns = {"/index.html"}
@MultipartConfig
@WebServlet(name = "HomeServlet", urlPatterns = {"/index.html"})//
public class HomeServlet extends HttpServlet {

  
    @EJB
    private DaoEjb daoEjb;    
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //метод срабытывает при запуске приложения     
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!doctype html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
        out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css\">");
        out.println("<link href=\"https://fonts.googleapis.com/css?family=Satisfy\" rel=\"stylesheet\">");
        out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
        out.println("<title>Телефонный справочник</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container menu\">");
        out.println("<nav id = \"navbar\" class=\"navbar navbar-expand-md navbar-light\">");
        out.println("<a class=\"navbar-brand\" href=\"#\" id =\"a_home\"><img src=\"img/new.png\" width=\"100\" height=\"100\" class=\"d-inline-block align-top\"> </a>");
        out.println("<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">");
        out.println("<span class=\"navbar-toggler-icon\"></span>");
        out.println("</button>");
        out.println("<div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">");
        out.println("<ul class=\"navbar-nav mr-auto\">");
        out.println("<li class=\"nav-item active\">");
        out.println("<a class=\"nav-link request_DB\" href=\"index.html\">Home <span class=\"sr-only\">(current)</span></a>");
        out.println("</li>");
        out.println("<li class=\"nav-item\">");
        out.println("<a class=\"nav-link socialIcon\" id =\"vkLogin\" href=\"#\"><i class=\"fa fa-fw fa-vk\"></i>VK</a>");
        out.println("</li>");
        out.println("<li class=\"nav-item\">");
        out.println("<a class=\"nav-link socialIcon\" id =\"fbLogin\" href=\"#\"><i class=\"fa fa-fw fa-facebook\"></i>Facebook</a>");
        out.println("</li>");
        out.println("<li class=\"nav-item\">");
        out.println("<a class=\"nav-link socialIcon\" href=\"#\"><i class=\"fa fa-fw fa-paper-plane\"></i>Telegram</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("<form class=\"form-inline mr-2\">");
        out.println("<div class=\"input-group mr-2\">");
        out.println("<span class=\"input-group-append\">");
        out.println("<button class=\"btn btn-success btn-cursor\" type=\"button\">");
        out.println("<i class=\"fa fa-user\"></i>");
        out.println("</button>");
        out.println("</span>");
        out.println("<input class=\"form-control py-2\" type=\"search\" placeholder=\"ФИО\" name=\"search_fio\" id=\"search-fio\">"); 
        out.println("</div>");         
        out.println("<div class=\"input-group mr-2\">");
        out.println("<button class=\"btn btn-success btn-cursor\" type=\"button\">");
        out.println("<i class=\"fa fa-phone\"></i>");
        out.println("</button>");
        out.println("<input class=\"form-control py-2\" type=\"search\" placeholder=\"Телефон\" name=\"search_phone\" id=\"search-phone\">");        
        out.println("</div>");
        out.println("<button class=\"btn btn-success request_DB\" type=\"submit\" id=\"btn_search\">Поиск</button>");
        out.println("</form>");
        out.println("</nav>");
        out.println("</div>");
        out.println("<div id = \"container_data\" class=\"container data\" >");
        String codevk = request.getParameter("code");
        if(codevk == null){
           List<Users> users = daoEjb.getAllUsers();
           displayDataPgSql(out, users,true); 
        }else{
           displayDataVk(out, codevk); 
        }
        out.println("</div>"); 
        out.println("<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"js/jquery.maskedinput.js\"></script>");
        out.println("<script src=\"https://connect.facebook.net/en_US/sdk.js\"></script>");
        out.println("<script src=\"js/main.js\"></script>");
        out.println("</body>");
        out.println("</html>");
        
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //метод срабатывает только если в форме заполненно хотя бы одно поле поиска
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");//если передаются русские символы
        PrintWriter out = response.getWriter();
        String home = request.getParameter("home");
        List<Users> users = null;
        if (home.equals("false")){
           users = daoEjb.findUsers(request.getParameter("search_fio"), request.getParameter("search_phone")); 
           displayDataPgSql(out, users, false); 
        }else{
           users = daoEjb.getAllUsers();
           displayDataPgSql(out, users, false); 
        }           
    }

     public void displayDataVk(PrintWriter out, String code){
        TransportClient transportClient = HttpTransportClient.getInstance(); 
        VkApiClient vk = new VkApiClient(transportClient); 
        try { 
            UserAuthResponse authResponse = vk.oauth().userAuthorizationCodeFlow(7022245, "6L4oNYBbFpOh7NST4ESK", "http://localhost:8080/Phonebook/index.html", code).execute();
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken()); 
            GetResponse friends = vk.friends().get(actor).execute();
            for (Integer fr : friends.getItems()){
                 List<UserXtrCounters> res = vk.users().get(actor).userIds(fr.toString()).fields(UserField.PHOTO_100).execute();
                 Status status = vk.status().get(actor).userId(fr).execute();
                 UserXtrCounters userFild = res.get(0);
                 out.println("<div class=\"row content_row justify-content-center \">");
                 out.println("<div class=\"col-4 align-self-center text-right\">");
                 out.println(" <img src=\"" + userFild.getPhoto100()  + "\" class=\"img-fluid img-circle\">"); 
                 out.println("</div>"); 
                 out.println("<div class=\"col-8 width-height-img align-self-center text-left text-white font-italic\"> " + userFild.getFirstName() + " " + userFild.getLastName() + " статус " + status.getText() + "</div>");
                 out.println("</div>"); 
                 out.println("<div class=\"row separator_row\">");
                 out.println("<div class=\"col-12\"><hr/></div>");
                 out.println("</div>");  
            
            }
        } catch (ApiException ex) {
           displayError(out, ex.getMessage());
        } catch (ClientException ex) {
           displayError(out, ex.getMessage());
        }
    }
    
    public void displayError(PrintWriter out, String strError){
        out.println("<div class=\"row content_row justify-content-center \">");
        out.println("<div class=\"col-4 align-self-center text-right\">");
        out.println(" <img src=\"img/not found db.png\" class=\"img-fluid img-circle\">"); 
        out.println("</div>"); 
        out.println("<div class=\"col-5 width-height-img align-self-center text-left text-white font-italic\"> " + strError + " </div>");
        out.println("</div>"); 
        out.println("<div class=\"row separator_row\">");
        out.println("<div class=\"col-12\"><hr/></div>");
        out.println("</div>"); 
    }
     
    public void displayDataPgSql(PrintWriter out, List<Users> users, boolean home){
        
       if (users.size()==0){
            //если вызывается home == true и users.size()==0 значит просто нет данных в базе 
            //если вызывается home == false и users.size()==0 значит пользователь ввел не правильные данные которых нет в базе 
            if(home == false){
              displayError(out, "c веденными параметрами нет данных");
            }
        }
        else{
            for (Users user : users) {
                 String name = user.getName();
                 String phone = user.getPhone();
                 out.println("<div class=\"row content_row justify-content-center \">");
                 out.println("<div class=\"col-4 align-self-center text-right\">");
                 String path_foto_user = "http://localhost:8080//images//" +  name + "_" + phone + ".jpg";
                 out.println(" <img src=\"" + path_foto_user  + "\" class=\"img-fluid img-circle\">"); 
                 out.println("</div>"); 
                 out.println("<div class=\"col-5 width-height-img align-self-center text-left text-white font-italic\"> " + name + " " + phone + "</div>");
                 out.println("</div>"); 
                 out.println("<div class=\"row separator_row\">");
                 out.println("<div class=\"col-12\"><hr/></div>");
                 out.println("</div>");  
            }
        }
    }
 
}
