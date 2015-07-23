package com.tt.keydemo;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;


public class DeviceListActivity extends Activity  {
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	public static String EXTRA_DEVICE_INFO = "device_info";
	
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;

	ArrayList<BluetoothDevice> deviceList;
	
	private ProgressBar bar;
	private Button btnScan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (D) Log.d(TAG, "onCreate()");
		
		setContentView(R.layout.device_list);
		setResult(Activity.RESULT_CANCELED);

		bar = (ProgressBar)findViewById(R.id.bar);
		bar.setVisibility(View.INVISIBLE);
		
		btnScan = (Button) findViewById(R.id.btn_scan);
		btnScan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doDiscovery();
				btnScan.setVisibility(View.GONE);
				bar.setVisibility(View.VISIBLE);
			}
		});

		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);

		ListView pairedListView = (ListView) findViewById(R.id.lv_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);


		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		deviceList = new ArrayList<BluetoothDevice>();
		
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
			    deviceList.add(device);
				mPairedDevicesArrayAdapter.add(device.getName());
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (D) Log.d(TAG, "onDestroy()");
		
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		this.unregisterReceiver(mReceiver);
	}

	private void doDiscovery() {
		if (D) Log.d(TAG, "doDiscovery()");

		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}

		mBtAdapter.startDiscovery();
	}

	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {

		    if (D) Log.d(TAG, "OnItemClickListener()");
		    
			mBtAdapter.cancelDiscovery();

			BluetoothDevice d = deviceList.get(position);
			String info = d.getName();
			String address = d.getAddress();

			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
			intent.putExtra(EXTRA_DEVICE_INFO, info);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			    
			    if (D) Log.d(TAG, "find new device");
			    
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				
				
				/*   读取设备所支持的UUID，必须配对后才能读到，否则为空
				
				System.out.println("-----------name=" + device.getName() + ",address=" + device.getAddress()
                      + ", class=" + device.getBluetoothClass().getMajorDeviceClass() + "," + device.getBluetoothClass().getDeviceClass());
				        
				ParcelUuid[] uuids = device.getUuids();
				if (uuids != null) {
    				for (ParcelUuid id : uuids) {
    				    System.out.println(id.getUuid().toString());
    				}
				}
				*/
				
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
				    
				    if (D) Log.d(TAG, "find new device -- 2");
				    
					int i = 0;
					for (i = 0; i < deviceList.size(); i++) {
					    if (deviceList.get(i).getAddress().equals(device.getAddress()))
					        break;
					}
					if (i >= deviceList.size()) {
					    deviceList.add(device);
					                                // name 有可能为 null
        				mPairedDevicesArrayAdapter.add(device.getName() == null ? device.getAddress() : device.getName());
        				mPairedDevicesArrayAdapter.notifyDataSetChanged();
					}
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
			    bar.setVisibility(View.INVISIBLE);
			    btnScan.setVisibility(View.VISIBLE);
			}
		}
	};

}
