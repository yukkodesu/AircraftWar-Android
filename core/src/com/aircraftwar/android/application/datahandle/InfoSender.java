package com.aircraftwar.android.application.datahandle;

import com.badlogic.gdx.Gdx;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class InfoSender {
    private Socket socket;
    private PrintWriter writer;

    public void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    //运行时修改成服务器的IP
                    socket.connect(new InetSocketAddress
                            ("10.0.2.2", 9999), 5000);
                    writer = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(), "UTF-8")), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void send(String str){
        if(writer != null && true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writer.println(str);
                }
            }).start();
        }
    }
}
