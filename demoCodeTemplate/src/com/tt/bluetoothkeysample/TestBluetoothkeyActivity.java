package com.tt.bluetoothkeysample;
//
//// standard demo

//
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.bluetooth.BluetoothAdapter;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bank.UInterface.IBank;
//import com.bank.key.IBankToken;

//
//
//public class TestBluetoothkeyActivity extends Activity implements OnClickListener {
//
//	IBank iToken = null;
//
//	final String TAG = "TestBluetoothkeyActivity";
//	private final int CODE_DEVICE_LIST = 1;
//	private final int CODE_TURN_ON_BT = 2;
//
//	private Button btnConnect;
//	private Button btnGetCn;
//	private Button btnGetCert;
//	private Button btnChangePin;
//	private Button btnSign;
//	private TextView tvConn;
//	private TextView tvCn;
//	private EditText etSign;
//	private ProgressDialog pDlg;
//	private Button btnGetSN;
//	private BluetoothAdapter btAdapter;
//	private boolean isConneced = false;
//
//	Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what == TokenManager.MSG_BLUETOOTH) {
//				if (msg.arg1 == TokenManager.BLUETOOTH_CONNECTED_SUCCESS) {
//					pDlg.dismiss();
//					showToast("Connect Success!");
//					tvConn.setText("Connect Success!");
//					pDlg.cancel();
//					isConneced = true;
//				} else if (msg.arg1 == TokenManager.BLUETOOTH_CONNECTED_FAILED) {
//					pDlg.dismiss();
//					showToast("Connect Failed!");
//					tvConn.setText("not connected.");
//					pDlg.cancel();
//					isConneced = false;
//				} else if(msg.arg1 == TokenManager.BLUETOOTH_CONNECTED_DISCONNECTED){
//					pDlg.dismiss();
//					tvConn.setText("not connected.");
//					isConneced = false;
//				}
//			}
//		};
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		pDlg = new ProgressDialog(this);
//		pDlg.setCancelable(false);
//
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
//		setContentView(R.layout.bankmain);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,  
//				R.layout.custom_title); 
//
//		btnConnect = (Button)findViewById(R.id.btn_connect);
//		btnGetCn = (Button)findViewById(R.id.btn_get_cn);
//		btnGetCert = (Button)findViewById(R.id.btn_get_cert);
//
//		btnChangePin = (Button)findViewById(R.id.btn_change_pin);
//		btnSign = (Button)findViewById(R.id.btn_sign);
//		tvConn = (TextView)findViewById(R.id.tv_connect);
//		tvCn = (TextView)findViewById(R.id.tv_cn);
//		etSign = (EditText)findViewById(R.id.et_sign);
//
//		btnGetSN = (Button)findViewById(R.id.btn_get_sn);
//
//		btnConnect.setOnClickListener(this);
//		btnGetCn.setOnClickListener(this);
//		btnGetCert.setOnClickListener(this);
//
//		btnChangePin.setOnClickListener(this);
//		btnSign.setOnClickListener(this);
//		btnGetSN.setOnClickListener(this);
//
//		iToken = new IBankToken();
//		
//		// We suggest you initialize by a handler , if you set the handler to null , 
//		// when you call connect(), you must implement a sound IBank.OnSafeCallback instance.
//		// When you call initialize(), the lib has starts to listen the bt connecting action already.
//		// So, maybe the connection has been completed before you call connect().
//
//		iToken.initialize(getApplicationContext(), handler);
//
//	}
//
//
//	/**
//	 *  If Bluetooth is turned off ,please turn on it.
//	 */
//	public void turnOnBT() {
//
//		btAdapter = BluetoothAdapter.getDefaultAdapter();
//		if (!btAdapter.isEnabled()) {
//			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(i, CODE_TURN_ON_BT);
//		} else {
//			startActivityForResult(new Intent(this, DeviceListActivity.class), CODE_DEVICE_LIST);
//		}
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == CODE_TURN_ON_BT) {
//			if (resultCode == Activity.RESULT_OK) {
//				startActivityForResult(new Intent(this, DeviceListActivity.class), CODE_DEVICE_LIST);
//			}
//		} else if (requestCode == CODE_DEVICE_LIST) {
//			if (resultCode == Activity.RESULT_OK) {
//				Bundle b = data.getExtras();
//				String addr = b.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//				String name = b.getString(DeviceListActivity.EXTRA_DEVICE_INFO);
//				connectBtKey(name, addr);
//			}
//		}
//	}
//
//	public void connectBtKey(String name, String addr) {  
//		showDlg("Connecting " + name);
//		
//		iToken.connect(new IBank.OnSafeCallback<String>(){
//			@Override
//			public void onResult(int errorCode, String result) {
//				// TODO Auto-generated method stub
//				System.out.println("connect errcode:"+errorCode);
//				if(errorCode == TokenManager.BLUETOOTH_CONNECTED_SUCCESS){
//					pDlg.dismiss();
//					tvConn.setText("Connect Successed");
//					pDlg.cancel();
//					isConneced = true;
//				}else if(errorCode == TokenManager.BLUETOOTH_PAIRING_FAILED){
//					pDlg.dismiss();
//					showToast("pair failed, please try to match it in the phone settings again");
//					tvConn.setText("pair failed");
//					pDlg.cancel();
//					isConneced = false;
//				}else{
//					pDlg.dismiss();
//					tvConn.setText("Connect Failed");
//					pDlg.cancel();
//					isConneced = false;
//				}
//			}}, addr);
//
//	}
//	/**
//	 *  Show a dialog ,please input the PIN of Bluetooth device, then sign.
//	 */
//	public void showSignDialog() {
//		final EditText etPsw = new EditText(this);
//		etPsw.setText("12345678");
//
//		AlertDialog.Builder bd = new AlertDialog.Builder(this);
//		bd.setTitle("Input PIN :");
//		bd.setView(etPsw);
//		bd.setCancelable(true);
//		bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				String psw = etPsw.getText().toString();
//				if (psw.length() > 0) {
//					String pin = etPsw.getText().toString().trim();
//
//					showDlg("Press the key. ");
//					String str = "<?xml version='1.0' encoding='utf-8'?><T><D>" +
//							"<M><k>付款账号:</k><v>6224745844485582</v></M>" +
//							"<M><k>金额:</k><v>258.50</v></M>" +
//							"<M><k>收款人账号:</k><v>6559524558521474</v></M></D><E>" +
//							"<M><k>流水号:</k><v>1234567890</v></M></E></T>";
//					iToken.signAsync( new IBank.OnSafeCallback<String>(){
//
//						@Override
//						public void onResult(int errorCode, String result) {
//							// TODO Auto-generated method stub
//							System.out.println("sign errorCode :"+errorCode);
//							pDlg.cancel();
//							etSign.setText(result);
//							tvCn.setText("sign"+":"+errorCode);
//						}
//					}, pin , str);
//
//				}
//			}
//		});
//		bd.create().show();
//	}
//
//
//	/**
//	 *  Show a dialog used to change PIN
//	 */
//	public void changePinDlg() {
//		View view = getLayoutInflater().inflate(R.layout.change_pin, null);
//		final EditText oldPin = (EditText)view.findViewById(R.id.et_old_pin);
//		final EditText newPin = (EditText)view.findViewById(R.id.et_new_pin);
//
//		AlertDialog.Builder bd = new AlertDialog.Builder(this);
//		bd.setView(view);
//		bd.setCancelable(true);
//		bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				String oldp = oldPin.getText().toString().trim();
//				String newp = newPin.getText().toString().trim();
//				showDlg("try to change pin...");
//				iToken.changePin(new IBank.OnSafeCallback<String>(){
//
//					@Override
//					public void onResult(int errorCode, String result) {
//						// TODO Auto-generated method stub
//						pDlg.cancel();
//						tvCn.setText("change pin"+":"+errorCode+":"+result);
//					}}, oldp, newp);
//			}
//		});
//
//		bd.create().show();
//	}
//
//	public void showToast(final String msg) {
//		runOnUiThread(new Runnable() {
//			public void run() {
//				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//
//	public void showDlg(final String msg) {
//		runOnUiThread(new Runnable() {
//			public void run() {
//				pDlg.setMessage(msg);
//				pDlg.show();
//			}
//		});
//	}
//
//	void showMsg(String msg) {
//		new AlertDialog.Builder(this).setTitle("飞天诚信").setMessage(msg)
//		.setPositiveButton("确认", null).create().show();
//	}
//	@Override
//	public void onClick(View v) {
//		if (v == btnConnect) {
//			turnOnBT();
//			return;
//		}
//
//		if(!isConneced){
//			showToast("KEY is not connected.");
//			tvCn.setText("");
//			return;
//		}
//
//		if (v == btnGetCn) {
//			showDlg("getting cn");
//			iToken.getCN(new IBank.OnSafeCallback<String>() {
//				@Override
//				public void onResult(int errorCode, String result) {
//					// TODO Auto-generated method stub
//					System.out.println("get cn errcode:"+errorCode);
//					pDlg.cancel();
//					tvCn.setText("getcn"+":"+errorCode);
//					if(errorCode==0){
//						showMsg(result);
//					}
//				}});
//		}
//
//		else if(v== btnGetCert){
//			showDlg("getting cert");
//			iToken.getCert(new IBank.OnSafeCallback<String>() {
//				@Override
//				public void onResult(int errorCode, String result) {
//					// TODO Auto-generated method stub
//					System.out.println("get cert errcode:"+errorCode);
//					pDlg.cancel();
//					tvCn.setText("getcert"+":"+errorCode);
//					if(errorCode==0){
//						showMsg(result);
//					}else{
//						tvCn.setText("getcert"+":"+errorCode+":"+result);
//					}
//				}});
//		} 
//		else if(v == btnGetSN){
//			showDlg("getting sn");
//			iToken.getSN(new IBank.OnSafeCallback<String>() {
//				@Override
//				public void onResult(int errorCode, String result) {
//					// TODO Auto-generated method stub
//					System.out.println("get sn errcode:"+errorCode);
//					pDlg.cancel();
//					tvCn.setText("getsn"+":"+errorCode+":"+result);
//					if(errorCode==0){
//						showMsg(result);
//					}
//				}
//			});
//			return;
//		}
//		else if (v == btnChangePin) {
//			changePinDlg();
//		} else if (v == btnSign) {
//			showSignDialog();
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		System.out.println("activity out.");
//		if(iToken!=null){
//			iToken.uninitialize(getApplicationContext());
//		}
//	}
//}
