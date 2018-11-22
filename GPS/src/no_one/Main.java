package no_one;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

public class Main {
static Parameter parameter=new Parameter();
	public static void main(String[] args) {
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date())+" 22"+":"+"33"+":"+"44";
		MysqlConnecter mysqlConnecter=new MysqlConnecter();
		String string="insert into gps(date) values('"+date+"')";
		mysqlConnecter.insert(string);
		System.out.println(string);	*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date;
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			Socket socket = serverSocket.accept(); // accept（）会阻塞当前线程 知道接收到一个socket
			InputStream inputStream = socket.getInputStream();
			byte[] bs = new byte[1024];
			int i = -1;
			int count = 1;
			String zhengZe = ".*GGA.*";
			String str;
			while ((i = inputStream.read(bs)) != -1) {

				str = new String(bs, 0, i);
				System.out.print(str);

				if (count == 1)
					count = 2;
				else {
					date = sdf.format(new Date());
					Show(str, date, zhengZe);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param str
	 * @param date
	 * @param zhengZe
	 */

	static void Show(String str, String date, String zhengZe) {
		int hour;
		String min;
		String sceond;
		String sql;
		Pattern pattern = Pattern.compile(zhengZe);
		Matcher mathcer = pattern.matcher(str);
		
		while (mathcer.find()) {
			System.out.println("已成功获取！");
			String str_matcher = mathcer.group(0);
			System.out.println(str_matcher);
			String[] array = str_matcher.split(",");

			if (array.length > 14) {
				if (array[2] == null || array[2].equals("")) {
					System.out.println("没有获取到GPS信号 ...");
				} else {
					hour = Integer.parseInt(array[1].substring(0, 2));
					min = array[1].substring(2, 4);
					sceond = array[1].substring(4, 6);

					if ((hour + 8) > 23) {
						hour += 8;
						hour -= 24;
					} else {
						hour += 8;
					}
					
					/**
					 * set()  get()方法
					 */
					String time=date+" "+hour+":"+min+":"+sceond;
					parameter.setUtc_time(time);
					parameter.setD_weiDu(Transformation(array[2]));
					parameter.setS_weiDu(array[3]);
					parameter.setD_jingDu(Transformation(array[4]));
					parameter.setS_jingDu(array[5]);
					parameter.setGps_xingNeng(array[6]);
					parameter.setGps_id(Integer.parseInt(array[7]));
					parameter.setJingQueDu(array[8]);
					parameter.setShuiPingMian_height(Integer.parseInt(array[9]));
					parameter.setSpm_height_danWei(array[10]);
					parameter.setXiangDui_height(Integer.parseInt(array[11]));
					parameter.setXd_height_danWei(array[12]);
					//parameter.setDgps(array[13]);
					parameter.setChaFenZhan_id(Integer.parseInt(array[13]));
					parameter.setJiaoYan(array[14]);
					
				}
			}

		}
	}
	
	 static String Transformation(int duShu) {
		
		return "";
	}

}
