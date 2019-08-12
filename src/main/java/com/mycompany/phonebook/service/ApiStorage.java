
package com.mycompany.phonebook.service;

import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.mtproto.auth.AuthKey;
import com.github.badoualy.telegram.mtproto.model.DataCenter;
import com.github.badoualy.telegram.mtproto.model.MTSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

public class ApiStorage implements TelegramApiStorage {
    public static final File AUTH_KEY_FILE = new File("auth.key");
    public static final File NEAREST_DC_FILE = new File("dc.save");
    
    
     public void saveAuthKey(AuthKey authKey) {
        try {
            FileUtils.writeByteArrayToFile(AUTH_KEY_FILE, authKey.getKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public AuthKey loadAuthKey() {
        try {
            AuthKey authKey = new AuthKey(FileUtils.readFileToByteArray(AUTH_KEY_FILE));
           /* System.out.println( authKey.getKey());
            System.out.println( authKey.getKeyId());
            System.out.println( authKey.getKeyIdAsLong());*/
            return authKey;
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }

        return null;
    }

    public void deleteAuthKey() {
        try {
            FileUtils.forceDelete(AUTH_KEY_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDc(DataCenter dataCenter) {
        try {
            FileUtils.writeStringToFile(NEAREST_DC_FILE, dataCenter.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public DataCenter loadDc() {
        try {
            String[] infos = FileUtils.readFileToString(NEAREST_DC_FILE).split(":");
            return new DataCenter(infos[0], Integer.parseInt(infos[1]));
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }

        return null;

    }

    public void deleteDc() {

        try {
            FileUtils.forceDelete(NEAREST_DC_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveSession(MTSession mtSession) {
         
    }

    @Nullable
    public MTSession loadSession() {
        return null;
    }
}
