/**
 * 作 者：Colin
 * 日 期：2015年6月23日下午6:55:21
 * 包 名：com.hhzt.iptv.lvb_x.interfaces
 * 工程名：LVB_X
 * 文件名：IOnDataCB.java
 */
package com.hhzt.iptv.lvb_x.interfaces;

public interface IOnRequestDataCB<T> {

	public abstract void onDataRequestSuccess(T t);

}
