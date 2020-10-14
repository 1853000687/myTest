package com.edu.test;

import com.edu.common.util.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class FtpTest {
    @Test
    public void test2() throws Exception{
        InputStream local = new FileInputStream("E:\\pic\\java.jpg") ;
        FtpUtil.uploadFile("101.201.155.26",21,"ftpuser","ftpuser","/home/ftpuser/www/images",
                "/2020/9/21","hello2.jpg",local);
    }
    @Test
    public void test() throws Exception{
        FTPClient client = new FTPClient() ;
        client.connect("101.201.155.26",21);
        client.user("ftpuser");
        client.pass("ftpuser");
        client.setFileType(FTP.BINARY_FILE_TYPE);// 以二进制的格式来进行保存图片
        client.changeWorkingDirectory("/home/ftpuser/www/images");// 图片保存的位置
        InputStream local = new FileInputStream("E:\\pic\\java.jpg") ;
        client.storeFile("hello.jpg",local) ;
        client.logout() ;// 关闭连接

    }
}
