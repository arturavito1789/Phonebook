
package com.mycompany.phonebook.service;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;



@Named("startTelegram") // эта аннотация преврашает класс в cdi бин и делает его видимым для jsf страницы
@SessionScoped
public class StartTelegram implements Serializable {
    private String kodTelegram;
    private TelegramApp application;
    private boolean accessed; //когда получаем доступ к телеграмму устанавливаем в истину
    private TelegramClient client;
    private TLSentCode sentCode;
    private String phohe_number;
    private String resultOperationTelegram;
    private ApiStorage state; 
    
    public StartTelegram() {
        this.application = new TelegramApp(868145, "f060da0313295164b197cea7de9f5e53", "Model",  "SysVer", "1", "en");
        this.resultOperationTelegram = "укажите номер телефона ";
        this.state = new ApiStorage();
        this.client = Kotlogram.getDefaultClient(application, state);
         
    }

    public TelegramApp getApplication() {
        return application;
    }

    public String getResultOperationTelegram() {
        return resultOperationTelegram;
    }

    public TelegramClient getClient() {
        return client;
    }

    public TLSentCode getSentCode() {
        return sentCode;
    }

    public boolean isAccessed() {
        return accessed;
    }

    public String getPhohe_number() {
        return phohe_number;
    }

    public void setPhohe_number(String phohe_number) {
        this.phohe_number = phohe_number;
    }    
    
    public String getKodTelegram() {
        return kodTelegram;
    }

    public void setKodTelegram(String kodTelegram) {
        this.kodTelegram = kodTelegram;
    }
   
    public String goToDataTelegram() {
        if ("".equals(kodTelegram)) {
            this.resultOperationTelegram = "укажите присланный код";
            return "no value code"; 
        } else{
           //код телеграмма введен переходим на основной сервлет HomeServlet
           //получаем данные из телеграмма и выводим.
           return "success";
        }
    }
    
    public void connectTelegram() { 
       /* Процедура срабатывает при получении кода телеграмм */  
        
      if ("".equals(phohe_number) == false) {
         try{
              sentCode = client.authSendCode(false, phohe_number, true);
              this.accessed = true; 
              this.resultOperationTelegram = "укажите полученный код";
           } catch (RpcErrorException | IOException e) {
              this.resultOperationTelegram  = e.getMessage();
           } 
       }  
       
    }  
    
    public void changedkodTelegram(ValueChangeEvent e) { 
       //процедура вызывается при изменении поля которое связано с бином в jsf странице но
       //вызывается она только когда срабатывает action формы.
       
    }  

}
