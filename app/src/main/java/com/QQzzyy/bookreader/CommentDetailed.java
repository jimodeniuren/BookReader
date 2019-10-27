package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentDetailed extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CircleImageView commentorAvator;
    TextView commentorName;
    TextView commentorNeirong;
    TextView commentNeirong;
    String commentid;
    public static final int UPDATE_TEXT = 3;
    private List<post> postList= new ArrayList<>();
    private CommentCommentAdaptor adapter;
    private post post =new post();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detailed);
        init();
        sendRequestWithOkHttp();
    }
    private void init() {
        post = (post) getIntent().getSerializableExtra("post");
        commentorAvator = (CircleImageView) findViewById(R.id.commentor_avator);
        commentorName = (TextView) findViewById(R.id.commentor_name);
        commentorNeirong = (TextView) findViewById(R.id.commentor_neirong);
        commentNeirong = (TextView) findViewById(R.id.comment_neirong);
        commentorName.setText(post.getAuthor().getNickname());
        commentorNeirong.setText(post.getTitle());
        commentid = post.get_id();
        Glide.with(this).load("https://statics.zhuishushenqi.com" + post.getAuthor().getAvatar()).into(commentorAvator);
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://api.zhuishushenqi.com/post/" + commentid ).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG,commentid);
                    Gson gson = new Gson();
                    SinglePostBean bean = gson.fromJson(responseData,SinglePostBean.class);
                    try {
                        post.setContent(bean.getPost().getContent());
                    }catch (Exception e){
                        post.setContent(bean.getHelp().getContent());
                    }
                    Request request2 = new Request.Builder().url("https://api.zhuishushenqi.com/post/" + commentid + "/comment").build();
                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    SingleCommentsBean bean2 = gson.fromJson(responseData2,SingleCommentsBean.class);
                    postList.addAll(bean2.getComments());
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    commentNeirong.setText(post.getContent());
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.comment_comment);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentDetailed.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new CommentCommentAdaptor(postList,CommentDetailed.this);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };
}
