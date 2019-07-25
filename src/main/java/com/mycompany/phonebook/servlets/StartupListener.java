/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.phonebook.servlets;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Артур
 */
public class StartupListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
      //события происходит при загрузке приложения. В файле tomee.xml указываем ContactDSJta
      //в настройках том ката в файле server указываем Context path="/images" docBase="C:\Foto_DB"
     System.out.println("перед запуском необходимо настроить файлы tomee.xml и server.xml. Фалы примеров приложены к проекту в каталоге META-INF.");
    }
        
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
           // Делаем что-то когда приложение останавливается
    }
    
}
