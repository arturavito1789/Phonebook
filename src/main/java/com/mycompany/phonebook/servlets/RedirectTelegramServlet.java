
package com.mycompany.phonebook.servlets;

import com.mycompany.phonebook.service.BinTelegram;
import com.mycompany.phonebook.service.StartTelegram;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "RedirectTelegramServlet", urlPatterns = {"/RedirectTelegramServlet"})
public class RedirectTelegramServlet extends HttpServlet {

    @Resource
    BeanManager beanManager;
   

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        /* запускается с home страницы если доступ к телеграмму был получен перенапрявляем HomeServlet */
        StartTelegram cdiTelegram =  BinTelegram.getBeanInstance(beanManager, StartTelegram.class);
        boolean accessed = cdiTelegram.isAccessed();
        if (accessed==true){
           request.getRequestDispatcher("getDataTelegram.xhtml").forward(request,response);
        }else{
           request.getRequestDispatcher("startTelegram.xhtml").forward(request,response); 
        }    
    }

   
}
