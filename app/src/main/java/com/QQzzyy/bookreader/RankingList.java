package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RankingList extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Book> bookArrayList= new ArrayList<>();
    private BookItemAdapter adapter;
    public static final int UPDATE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://api.zhuishushenqi.com/ranking/" + getIntent().getStringExtra("rankid")).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        Bean bean = gson.fromJson(jsonData,Bean.class);
        bookArrayList.addAll(bean.getRanking().getBooks());
    }

    private Handler handler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case UPDATE_TEXT:
                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.ranking_list_recycler_view);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RankingList.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        adapter = new BookItemAdapter(bookArrayList,RankingList.this);
                        recyclerView.setAdapter(adapter);
                        break;
                        default:
                            break;
                }
            }
    };
}
