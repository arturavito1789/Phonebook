
package com.mycompany.phonebook.servlets;


import com.mycompany.phonebook.dao.DaoEjb;
import com.mycompany.phonebook.entitys.Users;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//запрос post можно передавать несколькими способами либо через форму либо js объект XMLHttpRequest
//если через форму или при использовании js использовать объект FormData то нужна аннотация MultipartConfig
//в js можно использовать заголовок "application/x-www-form-urlencoded" и передавать параметры типа 
//"lorem=ipsum" тогда анотация MultipartConfig не нужна
@MultipartConfig
@WebServlet(name = "NewCreateUser", urlPatterns = {"/NewCreateUser"})
public class NewCreateUser extends HttpServlet {

    @EJB
    private DaoEjb daoEjb;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");//если передаются русские символы
        String fioParam = request.getParameter("fio");
        String phoneParam = request.getParameter("phone");
        List<Users> users = daoEjb.findUsers(fioParam, phoneParam);
        if (users.size() > 0){
            try (PrintWriter out = response.getWriter()) {
                 out.println("Уже существует пользователь с такими данными в базе");
            }
            return;
        }
      
        boolean res = daoEjb.saveUser(fioParam, phoneParam);
        if (res  == false){
            try (PrintWriter out = response.getWriter()) {
                 out.println("Ошибка при операции добавления в базу данных");
            }
            return;
        }
        
        for (Part part : request.getParts()) {
            if ("image/jpeg".equals(part.getContentType())){
                 String nameF = part.getName();
                 InputStream in = part.getInputStream();
                 OutputStream out = new FileOutputStream(new File("C:\\Foto_DB\\"+fioParam +"_" + phoneParam + nameF.substring(nameF.length() - 4)));
                 byte[] buf = new byte[1024*1024*10];
                 int len;
                 while((len=in.read(buf))>0){
                      out.write(buf,0,len);
                 }
                 out.flush();
                 out.close();
                 in.close(); 
             }
        }
       
        try (PrintWriter out = response.getWriter()) {
             out.println("операция прошла успешно");
        }
    }

  
}
