package com.yanshiqian.gps;

public class Parameter {

	private String dtu_id; // DTU的id
	private String utc_time; // UTC时间
	private double d_weiDu; // 纬度
	private String s_weiDu; // N=北纬 S=南纬
	private double d_jingDu; // 经度
	private String s_jingDu; // E=东经 W=西经
	private String gps_xingNeng; // GPS性能指示 0=未定位 1=误差分定位信息 2=带差分定位信息
	private int gps_id; // 使用卫星号 00-12
	private double jingQueDu; // 精度百分比
	private double shuiPingMian_height; // 大地水准面高度
	private String spm_height_danWei; // 天线高度单位 米
	private double xiangDui_height; // WSG-84大地椭球体海平面相对海平面的高度， 负数表示低于平均海平面
	private String xd_height_danWei; // 高度单位 米
	private String dgps; // 带差分GPS定位数据时间， 未使用DGPS时此字段为空
	private int chaFenZhan_id; // 差分站ID号 0000-1023
	private String jiaoYan; // 校验位

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public String getUtc_time() {
		return utc_time;
	}

	public void setUtc_time(String utc_time) {
		this.utc_time = utc_time;
	}

	public double getD_weiDu() {
		return d_weiDu;
	}

	public void setD_weiDu(double d_weiDu) {
		this.d_weiDu = d_weiDu;
	}

	public String getS_weiDu() {
		return s_weiDu;
	}

	public void setS_weiDu(String s_weiDu) {
		this.s_weiDu = s_weiDu;
	}

	public double getD_jingDu() {
		return d_jingDu;
	}

	public void setD_jingDu(double d_jingDu) {
		this.d_jingDu = d_jingDu;
	}

	public String getS_jingDu() {
		return s_jingDu;
	}

	public void setS_jingDu(String s_jingDu) {
		this.s_jingDu = s_jingDu;
	}

	public String getGps_xingNeng() {
		return gps_xingNeng;
	}

	public void setGps_xingNeng(String gps_xingNeng) {
		this.gps_xingNeng = gps_xingNeng;
	}

	public int getGps_id() {
		return gps_id;
	}

	public void setGps_id(int gps_id) {
		this.gps_id = gps_id;
	}

	public double getJingQueDu() {
		return jingQueDu;
	}

	public void setJingQueDu(double jingQueDu) {
		this.jingQueDu = jingQueDu;
	}

	public double getShuiPingMian_height() {
		return shuiPingMian_height;
	}

	public void setShuiPingMian_height(double shuiPingMian_height) {
		this.shuiPingMian_height = shuiPingMian_height;
	}

	public String getSpm_height_danWei() {
		return spm_height_danWei;
	}

	public void setSpm_height_danWei(String spm_height_danWei) {
		this.spm_height_danWei = spm_height_danWei;
	}

	public double getXiangDui_height() {
		return xiangDui_height;
	}

	public void setXiangDui_height(double xiangDui_height) {
		this.xiangDui_height = xiangDui_height;
	}

	public String getXd_height_danWei() {
		return xd_height_danWei;
	}

	public void setXd_height_danWei(String xd_height_danWei) {
		this.xd_height_danWei = xd_height_danWei;
	}

	public String getDgps() {
		return dgps;
	}

	public void setDgps(String dgps) {
		this.dgps = dgps;
	}

	public int getChaFenZhan_id() {
		return chaFenZhan_id;
	}

	public void setChaFenZhan_id(int chaFenZhan_id) {
		this.chaFenZhan_id = chaFenZhan_id;
	}

	public String getJiaoYan() {
		return jiaoYan;
	}

	public void setJiaoYan(String jiaoYan) {
		this.jiaoYan = jiaoYan;
	}
}
