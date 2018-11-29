package com.yanshiqian.gpstest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @创建人 yanshiqian
 * @创建时间 2018/11/28
 * @描述程序入口
 */

public class MainProject {


    public static void main(String[] args) {
        String dtuId = "";      //DTU的id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date;
        try {
            DatagramSocket socket = new DatagramSocket(8080);//监听端口
            byte[] data = new byte[1024];    // 确定数据报接受的数据的数组大小
            DatagramPacket packet = new DatagramPacket(data, data.length);// 创建接受类型的数据报，数据将存储在data中
            System.out.println("----已开启监听......");
            socket.receive(packet);// 通过套接字接收数据,在此之前会一直阻塞
            System.out.println("------------------已经连接......");


            int count = 1;
            String zhengZe = ".*GGA.*";
            String getData = null;
            getData = new String(data, 0, 1024);
            while (getData != null) {
                System.out.println("---------------------接收到的数据为：");
                System.out.println(getData);

                if (count == 1) {
                    dtuId = getData;
                    count = 2;
                } else {
                    date = sdf.format(new Date());
                    Thread thread = new Thread(new Implement(dtuId, getData, date, zhengZe));
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
