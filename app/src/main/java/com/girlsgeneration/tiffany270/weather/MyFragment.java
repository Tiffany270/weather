package com.girlsgeneration.tiffany270.weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tiffany270 on 2017/10/13.
 */

public class MyFragment extends android.support.v4.app.Fragment {

    private TextView title_city;
    private TextView body_temp;
    private TextView txt;
    private TextView qlty;
    private Bundle data;
    private Message message;
    private TextView hum,vis,dir,pres,uv,trav,sport;
    private View view;
    private static MyFragment firstFragment;

    //private TextView test;


    private String ID = "CN101010100";//默认id


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            String data = bundle.getString("tmp");
            Log.d("tag", "is: " + data);
            body_temp.setText(data + "℃");

            String txts = bundle.getString("txt");
            txt.setText(txts+" |  ");
            String qltys = bundle.getString("qlty");
            qlty.setText("空气 "+qltys);

            title_city.setText(bundle.getString("city"));
            uv.setText(bundle.getString("uv"));
            trav.setText(bundle.getString("comf"));
            sport.setText(bundle.getString("sport"));
            hum.setText(bundle.getString("hum")+"%");
            vis.setText(bundle.getString("vis"));
            dir.setText(bundle.getString("dir"));
            pres.setText(bundle.getString("pres"));


        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.firstcity_layout, null);
        body_temp = (TextView) view.findViewById(R.id.body_temp);
        title_city = (TextView) view.findViewById(R.id.title_city);
        txt= (TextView) view.findViewById(R.id.txt);
        qlty= (TextView) view.findViewById(R.id.qlty);
        hum = (TextView) view.findViewById(R.id.hum);
        pres = (TextView) view.findViewById(R.id.pres);
        dir = (TextView) view.findViewById(R.id.dir);
        vis= (TextView) view.findViewById(R.id.vis);

        uv = (TextView) view.findViewById(R.id.uv);
        sport = (TextView) view.findViewById(R.id.sport);
        trav= (TextView) view.findViewById(R.id.trav);

        //test = (TextView) view.findViewById(R.id.test);
        //test.setVisibility(view.INVISIBLE);





        Bundle mes = getArguments();
        if (mes != null) {
            String mgs = mes.getString("msg", "");

           // test.setText(mes.getString("test"));
           // title_city.setText(mgs);
            ID = mes.getString("id", "");
            Log.i("ID", ID);//返回城市id

        }


        new Thread(new myTherad()).start();//如果要更新可以直接New一个？

        return view;
    }



    public static MyFragment newIns(String msg,String tests) {

        firstFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        //传值

        Weathers cityname = new Weathers();
        String cityid = cityname.Weathers(msg);
        // Log.i("js", cityid);//返回城市id

        bundle.putString("id", cityid);

       // bundle.putString("test",tests);

        firstFragment.setArguments(bundle);


        return firstFragment;

    }

    public void  flash(String name){


        Weathers cityname = new Weathers();
        String cityid = cityname.Weathers(name);
        ID = cityid;
        new Thread(new myTherad()).start();


    }


    class myTherad implements Runnable {

        private String path = "https://free-api.heweather.com/v5/weather?city=" + ID +
                "&key=94f7df43b701480d827bd5fac454825f";

        @Override //这里写网络的耗时操作
        public void run() {

            Log.i("tag", ID);

            String str = null;
            StringBuilder sb = new StringBuilder();
            message = Message.obtain(handler);
            data = new Bundle();

            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while ((str = br.readLine()) != null) {
                        sb.append(str);
                    }
                }
            } catch (IOException e) {
                e.getStackTrace();
            }


            parseJsonMulti(sb.toString());

            message.setData(data);
            message.sendToTarget();

        }
    }


    private void parseJsonMulti(String strResult) {


        try {
            JSONObject jsonObject = new JSONObject(strResult);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            JSONObject jsonNow = jsonArray.getJSONObject(0);

            jsonNow = jsonNow.getJSONObject("now");//当天
            String tmp = jsonNow.getString("tmp");
            String pres = jsonNow.getString("pres");//气压
            String vis = jsonNow.getString("vis");
            String hum = jsonNow.getString("hum");

            JSONObject jsncond = jsonNow.getJSONObject("cond");
            String txt = jsncond.getString("txt");//晴
            JSONObject jsnwind = jsonNow.getJSONObject("wind");
            String dir = jsnwind.getString("dir");//东北风


            JSONObject jsonaqi = jsonArray.getJSONObject(0);
            jsonaqi=jsonaqi.getJSONObject("aqi");
            JSONObject jsncity = jsonaqi.getJSONObject("city");
            String qlty = jsncity.getString("qlty");//优


            JSONObject jsonsug = jsonArray.getJSONObject(0);
            jsonsug=jsonsug.getJSONObject("suggestion");//suggestion
            JSONObject jssport = jsonsug.getJSONObject("sport");
            String sport = jssport.getString("txt");

            JSONObject jsonuv = jsonsug.getJSONObject("uv");
            String uv = jsonuv.getString("txt");

            JSONObject jsontrav = jsonsug.getJSONObject("comf");
            String comf = jsontrav.getString("txt");

            JSONObject jsbaic = jsonArray.getJSONObject(0);
            jsbaic=jsbaic.getJSONObject("basic");
            String cityname =  jsbaic.getString("city");











            Log.i("city",strResult);
            Log.i("city",uv);
            Log.i("city",comf);


            data.putString("city",cityname);
            data.putString("comf",comf);
            data.putString("uv",uv);
            data.putString("sport",sport);
            data.putString("dir",dir);
            data.putString("pres",pres);
            data.putString("hum",hum);
            data.putString("vis",vis);
            data.putString("tmp", tmp);
            data.putString("txt",txt);
            data.putString("qlty",qlty);



        } catch (JSONException e) {
            System.out.println("Jsons parse error !");
            e.printStackTrace();
        }
    }//解析json数据




}



