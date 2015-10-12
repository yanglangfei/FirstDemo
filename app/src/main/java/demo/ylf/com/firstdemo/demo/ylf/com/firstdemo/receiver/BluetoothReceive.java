package demo.ylf.com.firstdemo.demo.ylf.com.firstdemo.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/9.
 */
public class BluetoothReceive extends BroadcastReceiver {
    private List<String> devices=new ArrayList<String>();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
            //结束蓝牙搜索
        }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())){
            //开始蓝牙搜索
        }else if(BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
            //发现设备
            BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Toast.makeText(context,"发现设备："+device.getAddress(),Toast.LENGTH_SHORT).show();
            devices.add(device.getAddress());
        }
    }
}
