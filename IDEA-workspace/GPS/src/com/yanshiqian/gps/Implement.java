package com.yanshiqian.gps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建人 yanshiqiann
 * @创建时间 2018/11/28
 * @描述 此类用于对接收到的数据进行分析处理，并将数据写入数据库
 */

public class Implement implements Runnable {

	/*
	 * 定义了一个退出标志exit，当exit为true时，while循环退出，
	 * exit的默认值为false.在定义exit时，使用了一个Java关键字volatile，
	 * 这个关键字的目的是使exit同步，也就是说在同一时刻只能由一个线程来修改exit的值.
	 */
	public volatile boolean exit = false;
	String dtuId = ""; // DTU的编号
	String str = ""; // 接收到的GPS数据
	String date = ""; // 接收到的系统时间（年月日），因为GPS数据本身只有时分秒，没有年月日
	String zhengZe = ""; // 正则表达式，要分析处理的数据格式

	public Implement(String dtuId, String str, String date, String zhengZe) {
		this.dtuId = dtuId;
		this.str = str;
		this.date = date;
		this.zhengZe = zhengZe;
	}

	public void run() {
		while (!exit) {
			Show(dtuId, str, date, zhengZe);
		}
	}

	/**
	 * 对接收到的数据进行分析处理，写入数据库
	 * 
	 */
	static Parameter parameter = new Parameter(); // set（） 和 get（）方法
	static MysqlConnecter mysqlConnecter = new MysqlConnecter(); // 连接数据库操作

	void Show(String dtuId, String str, String date, String zhengZe) {
		String hour;
		String min;
		String sceond;
		String sql;

		// 正则表达式，进行数据的筛选
		Pattern pattern = Pattern.compile(zhengZe);
		Matcher mathcer = pattern.matcher(str);
		while (mathcer.find()) {
			String str_matcher = mathcer.group(0); // 筛选出的数据
			System.out.println(str_matcher); // 显示正则筛选过后的数据内容
			String[] array = str_matcher.split(","); // 通过逗号，对数据内容进行分离，即子字符串

			if (array.length == 15) { // 判断数据的长度，判断是否是我们需要的信息
				if (array[2] == null || array[2].equals("")) {
					System.out.println("----------------未定位到位置信息......");
				} else {

					parameter.setDtu_id(dtuId);
					String Dtu_id = parameter.getDtu_id();
					System.out.println("DTU的id：" + Dtu_id);

					// 获取GPS采集数据的时间
					hour = array[1].substring(0, 2);
					min = array[1].substring(2, 4);
					sceond = array[1].substring(4, 6);

					parameter.setUtc_time(Time(date, hour, min, sceond));
					String Utc_time = parameter.getUtc_time();
					System.out.println("信息采集时间： " + Utc_time);

					parameter.setD_weiDu(Transformation(array[2]));
					double D_weiDu = parameter.getD_weiDu();

					parameter.setS_weiDu(array[3]);
					String S_weiDu = parameter.getS_weiDu();

					parameter.setD_jingDu(Transformation(array[4]));
					double D_jingDu = parameter.getD_jingDu();

					parameter.setS_jingDu(array[5]);
					String S_jingDu = parameter.getS_jingDu();
					System.out.println(D_weiDu + " " + S_weiDu + " , "
							+ D_jingDu + " " + S_jingDu);

					parameter.setGps_xingNeng(array[6]);
					String Gps_xingNeng = parameter.getGps_xingNeng();
					System.out.println("GPS性能：" + Gps_xingNeng);

					parameter.setGps_id(Integer.parseInt(array[7]));
					int Gps_id = parameter.getGps_id();
					System.out.println("接收到的GPS编号：" + Gps_id);

					parameter.setJingQueDu(Double.parseDouble(array[8]));
					double JingQueDu = parameter.getJingQueDu();
					System.out.println("GPS精度百分比：" + JingQueDu);

					parameter
							.setShuiPingMian_height(Integer.parseInt(array[9]));
					double ShuiPingMian_height = parameter
							.getShuiPingMian_height();
					System.out.println("大地水准面高度：" + ShuiPingMian_height);

					parameter.setSpm_height_danWei(array[10]);
					String Spm_height_danWei = parameter.getSpm_height_danWei();
					System.out.println("天线高度单位 米：" + Spm_height_danWei);

					parameter.setXiangDui_height(Integer.parseInt(array[11]));
					double XiangDui_height = parameter.getXiangDui_height();
					System.out.println("WSG-84大地椭球体海平面相对海平面的高度："
							+ XiangDui_height);

					parameter.setXd_height_danWei(array[12]);
					if (array[13].equals("") || array[13] == null) {
						array[13] = "0"; // 带差分GPS定位数据时间， 未使用DGPS时此字段为空,此处赋值为“0”
					}
					parameter.setDgps(array[13]);
					String Dgps = parameter.getDgps();
					System.out.println("带差分GPS定位数据时间：" + Dgps);

					// parameter.setChaFenZhan_id(Integer.parseInt(array[13]));

					parameter.setJiaoYan(array[14]);
					String JiaoYan = parameter.getJiaoYan();
					System.out.println("校验位：" + JiaoYan);

					sql = "INSERT INTO gps(gpsId,date,d_weidu,s_weidu,d_jingdu,s_jingdu,xingneng,gps_id,jingdu,shuizhunmian_height,tianxian_height_danwei,xiangdui_height,xiangdui_height_danwei,fenchazhan_id,jiaoyan) VALUES"
							+ " ('"
							+ parameter.getDtu_id()
							+ "','"
							+ parameter.getUtc_time()
							+ "','"
							+ parameter.getD_weiDu()
							+ "','"
							+ parameter.getS_weiDu()
							+ "','"
							+ parameter.getD_jingDu()
							+ "','"
							+ parameter.getS_jingDu()
							+ "','"
							+ parameter.getGps_xingNeng()
							+ "','"
							+ parameter.getGps_id()
							+ "','"
							+ parameter.getJingQueDu()
							+ "','"
							+ parameter.getShuiPingMian_height()
							+ "','"
							+ parameter.getSpm_height_danWei()
							+ "','"
							+ parameter.getXiangDui_height()
							+ "','"
							+ parameter.getXd_height_danWei()
							+ "','"
							+ parameter.getChaFenZhan_id()
							+ "','"
							+ parameter.getJiaoYan() + "')";

					// 写入数据库操作，并返回操作码
					int response = 0;
					response = mysqlConnecter.insert(sql);
					if (response == 0) {
						System.out.println("-----------插入数据库失败！！！---------");
					}
				}
			}
		}
	}

	/**
	 * 转换经纬度
	 * 
	 * @param duShu
	 * @return
	 */
	static double Transformation(String duShu) {
		double number = Double.parseDouble(duShu);
		double degrees = number / 10000;
		return degrees;
	}

	static String Time(String date, String hour, String min, String sceond) {
		// GPS时间加上八个时区
		int now_hour = Integer.parseInt(hour) + 8;
		if (now_hour > 23) {
			now_hour -= 24;
		}
		String time = date + " " + now_hour + ":" + min + ":" + sceond;
		return time;
	}
}
