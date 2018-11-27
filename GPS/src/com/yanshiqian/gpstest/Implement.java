package com.yanshiqian.gpstest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建人 yanshiqian
 * @创建时间 2018/11/28
 * @描述
 */

public class Implement implements Runnable {

    String dtuId = "";
    String str = "";
    String date = "";
    String zhengZe = "";

    public Implement(String dtuId, String str, String date, String zhengZe) {
        this.dtuId = dtuId;
        this.str = str;
        this.date = date;
        this.zhengZe = zhengZe;
    }

    @Override
    public void run() {
        Show(dtuId, str, date, zhengZe);
    }

    /**
     * 对接收到的数据进行分析处理，写入数据库
     *
     * @param str
     * @param date
     * @param zhengZe
     */
    static Parameter parameter = new Parameter();
    static MysqlConnecter mysqlConnecter = new MysqlConnecter();

    void Show(String dtuId, String str, String date, String zhengZe) {
        String hour;
        String min;
        String sceond;
        String sql;

        //正则表达式，进行数据的筛选
        Pattern pattern = Pattern.compile(zhengZe);
        Matcher mathcer = pattern.matcher(str);
        while (mathcer.find()) {
            System.out.println("----------筛选出数据:");
            String str_matcher = mathcer.group(0);
            System.out.println(str_matcher);    //显示正则筛选过后的数据内容
            String[] array = str_matcher.split(",");    //通过逗号，对数据内容进行分离

            if (array.length == 15) {    //判断数据的长度，判断是否是我们需要的信息
                if (array[2] == null || array[2].equals("")) {
                    System.out.println("----------------未定位到位置信息......");
                } else {
                    hour = array[1].substring(0, 2);
                    min = array[1].substring(2, 4);
                    sceond = array[1].substring(4, 6);
                    /**
                     * set()  get()
                     */
                    parameter.setDtu_id(dtuId);
                    parameter.setUtc_time(Time(date, hour, min, sceond));
                    parameter.setD_weiDu(Transformation(array[2]));
                    parameter.setS_weiDu(array[3]);
                    parameter.setD_jingDu(Transformation(array[4]));
                    parameter.setS_jingDu(array[5]);
                    parameter.setGps_xingNeng(array[6]);
                    parameter.setGps_id(Integer.parseInt(array[7]));
                    parameter.setJingQueDu(Double.parseDouble(array[8]));
                    parameter.setShuiPingMian_height(Integer.parseInt(array[9]));
                    parameter.setSpm_height_danWei(array[10]);
                    parameter.setXiangDui_height(Integer.parseInt(array[11]));
                    parameter.setXd_height_danWei(array[12]);
                    if (array[13].equals("") || array[13] == null) {
                        array[13] = "0";      // 带差分GPS定位数据时间， 未使用DGPS时此字段为空,此处赋值为“0”
                    }
                    parameter.setDgps(array[13]);
                    //parameter.setChaFenZhan_id(Integer.parseInt(array[13]));
                    parameter.setJiaoYan(array[14]);
                    sql = "INSERT INTO gps(gpsId,date,d_weidu,s_weidu,d_jingdu,s_jingdu,xingneng,gps_id,jingdu,shuizhunmian_height,tianxian_height_danwei,xiangdui_height,xiangdui_height_danwei,fenchazhan_id,jiaoyan) VALUES"
                            + " ('"
                            + parameter.getDtu_id() + "','"
                            + parameter.getUtc_time() + "','"
                            + parameter.getD_weiDu() + "','"
                            + parameter.getS_weiDu() + "','"
                            + parameter.getD_jingDu() + "','"
                            + parameter.getS_jingDu() + "','"
                            + parameter.getGps_xingNeng() + "','"
                            + parameter.getGps_id() + "','"
                            + parameter.getJingQueDu() + "','"
                            + parameter.getShuiPingMian_height() + "','"
                            + parameter.getSpm_height_danWei() + "','"
                            + parameter.getXiangDui_height() + "','"
                            + parameter.getXd_height_danWei() + "','"
                            + parameter.getChaFenZhan_id() + "','"
                            + parameter.getJiaoYan() + "')";

                    //写入数据库操作，并返回操作码
                    int response = 0;
                    response = mysqlConnecter.insert(sql);
                    if (response == 0) {
                        System.out.println("-------------------------插入数据库失败！！！---------");
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
        //GPS时间加上八个时区
        int now_hour = Integer.parseInt(hour) + 8;
        if (now_hour > 23) {
            now_hour -= 24;
        }
        String time = date + " " + now_hour + ":" + min + ":" + sceond;
        return time;
    }
}
