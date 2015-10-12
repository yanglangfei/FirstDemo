package demo.ylf.com.firstdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;

import demo.ylf.com.firstdemo.demo.ylf.com.model.WeatherInfo;

public class ChatActivity extends Activity implements View.OnClickListener {

    private EditText et_area;
    private Button btn_search;
    private TextView tv_info;
    private Context context;
    private  RequestQueue queue;
    private  SpeechSynthesizer synthesizer;
    private ProgressDialog dialog;
    private LocationClient client;
    private BDLocationListener listener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int locationType=bdLocation.getLocType();
            switch (locationType){
                case BDLocation.TypeGpsLocation:
                    //GPS 定位
                    speekLocation(bdLocation);
                    break;
                case  BDLocation.TypeNetWorkLocation:
                    //网络定位
                    speekLocation(bdLocation);
                    break;
                case  BDLocation.TypeOffLineLocation:
                    //离线定位
                    speekLocation(bdLocation);
                    break;
                case  BDLocation.TypeServerError:
                    //服务器端异常
                    synthesizer.speak("主人，您的正处于危险地区...");
                    client.stop();
                    break;
                case  BDLocation.TypeNetWorkException:
                    //网络异常
                    synthesizer.speak("主人，开了网络在告诉你位置吧，嘻嘻");
                    client.stop();
                    break;
                case  BDLocation.TypeCriteriaException:
                    //手机状态异常
                    synthesizer.speak("主人，您的手机今天忘记吃药了..");
                    client.stop();
                    break;
                default:
                    break;

            }
        }
    };
    private  String API_KEY="IQnAlR7Vxmtn0jsTh23oEFhQ";
    private String SECRT_KEY="6940bf1fafc05c473746d240d7e1cd1e";
    private static final String WEATHER_PATH = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=天气";
    private Handler mHandle=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    //请求成功
                    dialog.cancel();
                    WeatherInfo info=JSON.parseObject(msg.obj.toString(), WeatherInfo.class);
                    synthesizer.speak(info.getContent());
                    tv_info.setText(info.getContent());
                    break;
                case  200:
                    //获取信息失败
                    dialog.cancel();
                    tv_info.setText("请求失败！！！");
                    synthesizer.speak("对不起，主人，我错了");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        initWidget();
        initVoiceParam();
        initLocationParam();
    }

    private void initWidget() {
        context=ChatActivity.this;
        queue= Volley.newRequestQueue(context);
        et_area=(EditText)findViewById(R.id.et_area);
        btn_search= (Button) findViewById(R.id.btn_search);
        tv_info= (TextView) findViewById(R.id.tv_info);
        btn_search.setOnClickListener(this);
        dialog=new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("查询提示");
        dialog.setMessage("正在查询，请稍等...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    /**
     * 初始化百度定位引擎
     */
    private void initLocationParam() {
        client=new LocationClient(context);
        client.registerLocationListener(listener);
        LocationClientOption option=new LocationClientOption();
        //高精度定位模式（默认）
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        client.setLocOption(option);
        client.start();
    }

   /*
     初始化百度语音合成引擎
    */
    private void initVoiceParam() {
        synthesizer=new SpeechSynthesizer(context, "holder", new SpeechSynthesizerListener() {
            @Override
            public void onStartWorking(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {

            }

            @Override
            public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

            }

            @Override
            public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

            }

            @Override
            public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onCancel(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {

            }

            @Override
            public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {

            }
        });
        synthesizer.setApiKey(API_KEY, SECRT_KEY);
        synthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, SpeechSynthesizer.SPEAKER_FEMALE);

    }

    public  void speekLocation(BDLocation dblocation){
        String address=dblocation.getAddrStr();
        synthesizer.speak("主人，您当前位置："+address);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_search:
                dialog.show();
                final String area=et_area.getText().toString().trim();
                if(area.length()>0) {
                    new Thread() {
                        @Override
                        public void run() {
                            StringRequest request = new StringRequest(Request.Method.GET, WEATHER_PATH + area, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    mHandle.obtainMessage(100, s).sendToTarget();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    mHandle.obtainMessage(200).sendToTarget();
                                    ;
                                }
                            });
                            queue.add(request);
                        }
                    }.start();
                }else {
                    synthesizer.speak("主人，给我分配个实际点的任务吧..");
                }
                break;
            default:
                break;
        }

    }
}
