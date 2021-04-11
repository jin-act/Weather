package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class MainActivity<serviceKey> extends AppCompatActivity {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private Button btn1;
    private long Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] parseArray = new String[153];
        int i = 0;
        tv1 = (TextView)findViewById(R.id.TV1);
        tv2 = (TextView)findViewById(R.id.TV2);
        tv3 = (TextView)findViewById(R.id.TV3);
        tv4 = (TextView)findViewById(R.id.TV4);
        btn1= (Button)findViewById(R.id.btn1);
        tv1.setText("aaa");
        btn1.setOnClickListener(new View.OnClickListener() {
            String [] parseArray =new String[153];
            @Override
            public void onClick(View view) {
                // Thread를 생성한다.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        try {

                            String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
                            // 홈페이지에서 받은 키
                            String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
                            String pageNo = "1";
                            String numOfRows = "153";    //한 페이지 결과 수
                            String dataType = "xml";    //타입 xml, json 등등 .
                            String base_date = "20210411";    //조회하고싶은 날짜 이 예제는 어제 날짜 입력해 주면 됨// .
                            String base_time = "0500";    //API 제공 시간을 입력하면 됨
                            String nx = "60";    //위도
                            String ny = "127";    //경도
                            String url = apiUrl + "?" + "servicekey=" + serviceKey
                                    + "&" + "pageNo=" + pageNo + "&" + "numOfRows=" + numOfRows
                                    + "&" + "dataType=" + dataType + "&" + "base_date=" + base_date
                                    + "&" + "base_time=" + base_time + "&" + "nx=" + nx
                                    + "&" + "ny=" + ny;
                            System.out.println(url);
                            Document document = Jsoup.connect(url).get();
                            Elements links = document.select("body items item category");
                            for(Element element : links){
                                parseArray[i++]=element.text();
                            }
                            System.out.println(parseArray[0]);
                            System.out.println(parseArray[1]);
                            System.out.println(parseArray[2]);
                            System.out.println(parseArray[3]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv1.setText(parseArray[0]);
                                tv2.setText(parseArray[1]);
                                tv3.setText(parseArray[2]);
                                tv4.setText(parseArray[3]);
                            }
                        });
                    }
                }).start();
            }
        });
    }


    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-Back<2000){
            finish();
        }
        Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        Back=System.currentTimeMillis();
    }
}
