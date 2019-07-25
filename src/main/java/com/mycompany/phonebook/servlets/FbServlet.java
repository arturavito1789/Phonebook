/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebook.servlets;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Артур
 */
@MultipartConfig
@WebServlet(name = "FbServlet", urlPatterns = {"/FbServlet"})
public class FbServlet extends HttpServlet {
       
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
       response.setCharacterEncoding("UTF-8");
       PrintWriter out = response.getWriter();
       FacebookClient fbClient = new DefaultFacebookClient(request.getParameter("accessToken"),Version.VERSION_3_3);
       Connection<User> myFriends = fbClient.fetchConnection("me/friends", User.class);
            for (List<User> myFriend : myFriends) {
                for (User friend : myFriend) {
                    out.println("<div class=\"row content_row justify-content-center \">");
                    out.println("<div class=\"col-4 align-self-center text-right\">");
                    out.println("<script src=\"https://connect.facebook.net/en_US/sdk.js\"></script>");
                    out.println(" <img src=\"https://graph.facebook.com/" + friend.getId() + "/picture"  + "\" class=\"img-fluid img-circle\">"); 
                    out.println("</div>"); 
                    out.println("<div class=\"col-8 width-height-img align-self-center text-left text-white font-italic\"> " + friend.getName() + "</div>");
                    out.println("</div>"); 
                    out.println("<div class=\"row separator_row\">");
                    out.println("<div class=\"col-12\"><hr/></div>");
                    out.println("</div>");                      
                }
            }
    }


}
