package com.hhzt.iptv.lvb_x.model;

public class DeviceInfoBean {
	private int status;
	private int countdownsecond;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCountdownSecond() {
		return countdownsecond;
	}

	public void setCountdownSecond(int countdownSecond) {
		this.countdownsecond = countdownSecond;
	}

	@Override
	public String toString() {
		return "DeviceInfoBean [status=" + status + ", countdownsecond=" + countdownsecond + "]";
	}

}
