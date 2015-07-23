package com.tt.automationTest;

import java.io.InputStream;
import java.io.OutputStream;

// automation test , the cmd could be any source , a file ,or from web.
public class AutomationTestKey {

	final static int CMD_SRC_FILE = 1;
	final static int CMD_SRC_WEB = 2;

	// cmdSrc is the source of CMD , file , or web ,ect .
	// define in this class .
	// this class will read from in , and write to out stream in another thread .
	void init(int cmdSrc , InputStream in, OutputStream out){

	}

	void uninit(){

	}

	void run(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// while !exit
				// get cmd
				// send cmd
				// return result
			}}).start();
	}

	// do this in run()
	byte [] getCmd(){ 
		byte re[] = null;

		return re;
	}

	// do this in run()
	byte[] sendCmd(byte sendData[]){ // send the cmd to device , and the device will return some data .
		byte re[] = null;

		return re;		
	}

}
