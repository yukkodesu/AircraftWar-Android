package com.aircraftwar.android.application.datahandle;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScoreSyncer {
    private Socket socket;
    private List<Score> scoresFromServer;

    public List<Score> syncScore(List<Score> scores) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                socket = new Socket();
                //运行时修改成服务器的IP
                try {
                    socket.connect(new InetSocketAddress
                            ("192.168.3.106", 10086), 5000);

                    ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
                    objOut.writeObject(scores);
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    scoresFromServer = (ArrayList<Score>) in.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return scoresFromServer;
    }
}
