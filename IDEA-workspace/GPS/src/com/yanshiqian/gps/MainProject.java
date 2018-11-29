package com.yanshiqian.gps;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @创建人 yanshiqian
 * @创建时间 2018/11/28
 * @描述 主程序入口
 */

public class MainProject {

	public static void main(String[] args) {
		String dtuId = ""; // 保存DTU的id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date;
		try {
			DatagramSocket socket = new DatagramSocket(8010);// 监听端口
			byte[] data = new byte[1024]; // 确定数据报接受的数据的数组大小
			DatagramPacket packet = new DatagramPacket(data, data.length); // 创建接受类型的数据报，数据将存储在data中
			System.out.println("----已开启监听......");
			int count = 1;
			while (true) {
				socket.receive(packet); // 通过套接字接收数据,在此之前会一直阻塞
				if (count == 1)
					System.out.println("------------------已经连接!!!");

				String zhengZe = ".*GGA.*"; // 添加正则表达式
				String getData = null; // 获得UDP传过来到数据
				getData = new String(data, 0, 1024);

				if (getData != null) {

					if (count == 1) { // UDP第一次穿过来的数据是DTU的编号（此编号是我们在配置DTU时候设置的）
						dtuId = getData;
						count = 2;
					} else {
						date = sdf.format(new Date()); // 获取系统当前时间（年月日），因为GPS的时间只有时分秒
						Thread thread = new Thread(new Implement(dtuId,
								getData, date, zhengZe));
						thread.start();
						System.out.println("共计："+(count++)+" 次");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
