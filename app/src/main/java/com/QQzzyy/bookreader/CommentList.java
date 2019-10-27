package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CommentList extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<post> postList= new ArrayList<>();
    private CommentListAdapter adapter;
    public static final int UPDATE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://api.zhuishushenqi.com/post/" + getIntent().getStringExtra("commentid")).build();
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
        PostBean bean = gson.fromJson(jsonData,PostBean.class);
        try {
            postList.addAll(bean.getPosts());
        }
        catch (Exception e){
        }
        try {
            postList.addAll(bean.getHelps());
            String s ;
            for (post p :postList)
            {
                s = p.get_id();
                s = "help/" + s;
                p.set_id(s);
            }
        }
        catch (Exception e){
        }

    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.comment_list_recycler_view);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentList.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new CommentListAdapter(postList,CommentList.this);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };
}
