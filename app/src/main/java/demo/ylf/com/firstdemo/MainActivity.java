package demo.ylf.com.firstdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/8.
 */
public class MainActivity extends Activity {

    private Context context=MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        BluetoothManager bluetoothManager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
        //直接打开蓝牙
        adapter.enable();
        //关闭蓝牙
        adapter.disable();
        //打开系统蓝牙设置界面
        Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent,100);
        //打开蓝牙搜索功能
        Intent discover=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //设置蓝牙搜索时间
        discover.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,3000);
        adapter.startDiscovery();
        //取消搜索
        adapter.cancelDiscovery();

        try {
            BluetoothServerSocket serverSocket=adapter.listenUsingRfcommWithServiceRecord(adapter.getName(), UUID.randomUUID());
            BluetoothSocket socket=serverSocket.accept();
            InputStream is=socket.getInputStream();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BluetoothDevice device=adapter.getRemoteDevice(adapter.getAddress());
        try {
            BluetoothSocket clientSocket=device.createInsecureRfcommSocketToServiceRecord(UUID.randomUUID());
            clientSocket.connect();
            InputStream is=clientSocket.getInputStream();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
