package com.hs.blog.service;


import com.hs.blog.xo.Result;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;


@Configuration
public class ImageService {

    @Value("${FTP.ADDRESS}")
    private String host;
    @Value("${FTP.PORT}")
    private int port;
    @Value("${FTP.USERNAME}")
    private String username;
    @Value("${FTP.PASSWORD}")
    private String password;
    @Value("${FTP.BASEPATH}")
    private String basepath;


    public Result upLoad(String fileName, InputStream input){
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            reply = ftpClient.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                return new Result(false, 400, "ftp connection problem", null);
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(basepath);

            // upload image
            if(!ftpClient.storeFile(fileName, input)){
                return new Result(false, 400, "upload failure", null);
            }

            input.close();
            ftpClient.logout();

        }catch (IOException e){
            e.printStackTrace();

        }
        finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return new Result(true, 200, "upload ok", null);

    }
}
