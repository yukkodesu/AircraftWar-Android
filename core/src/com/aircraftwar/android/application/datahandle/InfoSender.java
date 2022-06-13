package com.aircraftwar.android.application.datahandle;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class InfoSender {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader in;
    private String tmp;
    private boolean getStart = false;
    private boolean getEnd = false;

    public void connect() {
        Gdx.app.log("INFOSENDER", "start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    //运行时修改成服务器的IP
                    socket.connect(new InetSocketAddress
                            ("192.168.3.98", 9999), 5000);
                    writer = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(), "UTF-8")), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }).start();
    }

    public void send(String str) {
        if (writer != null) {
            writer.println(str);
        }
    }

    public String get() throws IOException {
        if (!getStart) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!getEnd) {
                        try {
                            if (in != null) {
                                tmp = in.readLine();
                                if (tmp != null) {
                                    getStart = true;
                                    if (tmp.equals("end")) {
                                        getEnd = true;
                                        Gdx.app.log("INFOSENDER", "end");
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            if (tmp != null && !tmp.equals("end")) return tmp;
        }
        return "-1";
    }
}
