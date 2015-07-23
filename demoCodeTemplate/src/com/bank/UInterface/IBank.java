package com.bank.UInterface;

import android.content.Context;
import android.os.Handler;

public abstract interface IBank{
	
	final public static int MSG_BLUETOOTH = 24000500 ;    
	final public static int MSG_TRANSFERSTATION = 24000601;         
	final public static int BLUETOOTH_CONNECTED_SUCCESS = 24000501;
	final public static int BLUETOOTH_CONNECTED_FAILED = 240005002;
	final public static int BLUETOOTH_CONNECTED_DISCONNECTED = 24000503;

	public interface OnSafeCallback<T> {
		/**
		 * 执行结果回调函数
		 * @param errorCode 错误码
		 * @param result 可变长参数，作为结果返回值
		 */
		void onResult(int errorCode, T result);
	}

	public abstract int getVersionCode();

	public abstract String getVersionName();
	
	public abstract boolean initialize(Context paramContext , Handler h);
	
	public abstract boolean uninitialize(Context paramContext);
	
	public abstract void connect(OnSafeCallback<String> connectCallback , String devMacAddr);
	
	public abstract void getSN(OnSafeCallback<String> snCallback);
	
	public abstract void getCN(OnSafeCallback<String> cnCallback);
	
	public abstract void getCert(OnSafeCallback<String> certCallback);

	public abstract void signAsync(OnSafeCallback<String> signCallback, String paramPasswdStr , String signData);

	public abstract void changePin(OnSafeCallback<String> changePinCallback,String oldPin, String newPin);
	
	//public abstract void showManagementTool(Context paramContext);

}