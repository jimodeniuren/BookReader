package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookIntroduction extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView bookCover;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookShortIntroduction;
    String bookid;
    public static final int UPDATE_TEXT = 3;
    private List<chapter> stringArrayList= new ArrayList<>();
    private StringArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_introduction);
        init();
        sendRequestWithOkHttp();
    }

    private void init(){
        final Book book = (Book) getIntent().getSerializableExtra("book");
        bookCover = (ImageView)findViewById(R.id.book_introduction_cover);
        bookTitle = (TextView)findViewById(R.id.book_introduction_title);
        bookAuthor = (TextView)findViewById(R.id.book_introduction_author);
        bookShortIntroduction = (TextView)findViewById(R.id.book_introduction_short_introduction);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookShortIntroduction.setText(book.getShortIntro());
        bookid = book.get_id();
        Glide.with(this).load("https://statics.zhuishushenqi.com" + book.getCover()).into(bookCover);
        Button button = (Button)findViewById(R.id.add_to_book_rack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikedBook likedBook = new LikedBook();
                likedBook.init(book);
                if (DataSupport.where("bookId = ?",likedBook.get_id()).find(LikedBook.class).size() == 0) {
                    likedBook.save();
                    Toast.makeText(BookIntroduction.this, "已经添加到书架", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(BookIntroduction.this, "这本书已经在书架上了", Toast.LENGTH_SHORT).show();
            }
        });
        Button button2 = (Button)findViewById(R.id.delete_from_book_rack);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataSupport.where("bookId = ?",book.get_id()).find(LikedBook.class).get(0).delete();
                     Toast.makeText(BookIntroduction.this,"已经从书架上移除",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(BookIntroduction.this,"书架上还没有这本书",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRequestWithOkHttp(){
        Toast.makeText(BookIntroduction.this,"正在搜索章节资源，请稍候",Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://novel.juhe.im/book-sources?view=summary&book=" + bookid ).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    List<SourceId> bean = gson.fromJson(responseData,new TypeToken<List<SourceId>>(){}.getType());
                    Request request2 = new Request.Builder().url("https://novel.juhe.im/book-chapters/" + bean.get(0).get_id()).build();
                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    mtBean bean2 = gson.fromJson(responseData2,mtBean.class);
                    stringArrayList.addAll(bean2.getChapters());
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
                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.chapters);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookIntroduction.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new StringArrayAdapter(stringArrayList,BookIntroduction.this);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };
}
