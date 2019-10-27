package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.QQzzyy.bookreader.FlipperLayout;
import com.QQzzyy.bookreader.FlipperLayout.TouchListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Reading extends Activity implements OnClickListener, TouchListener {
    private static final String TAG = "MainActivity";
    private String text = "";
    private int textLenght = 0;
    private static final int COUNT = 880;
    private int currentTopEndIndex = 0;
    private int currentShowEndIndex = 0;
    private int currentBottomEndIndex = 0;
    private Bundle args ;
    private ArrayList<chapter> chapters = new ArrayList<>();
    private int position ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        args = getIntent().getBundleExtra("BUNDLE");
        chapters = (ArrayList<chapter>) args.getSerializable("chapters");
        position = getIntent().getIntExtra("position", 0);
        if (hasNavBar(this))
            hideBottomUIMenu();
        sendRequestWithOkHttp();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            FlipperLayout rootLayout = (FlipperLayout) findViewById(R.id.container2);
            View recoverView = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);
            View view1 = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);
            View view2 = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);
            rootLayout.initFlipperViews(Reading.this, view2, view1, recoverView);
            textLenght = text.length();
            TextView textView = (TextView) view1.findViewById(R.id.textview);
            if (textLenght > COUNT) {
                textView.setText(text.subSequence(0, COUNT));
                textView = (TextView) view2.findViewById(R.id.textview);
                if (textLenght > (COUNT << 1)) {
                    textView.setText(text.subSequence(COUNT, COUNT * 2));
                    currentShowEndIndex = COUNT;
                    currentBottomEndIndex = COUNT << 1;
                } else {
                    textView.setText(text.subSequence(COUNT, textLenght));
                    currentShowEndIndex = textLenght;
                    currentBottomEndIndex = textLenght;
                }
            } else {
                textView.setText(text.subSequence(0, textLenght));
                currentShowEndIndex = textLenght;
                currentBottomEndIndex = textLenght;
            }
        }

        ;
    };

    @Override
    public void onClick(View v) {
    }

    @Override
    public View createView(final int direction) {
        String txt = "";
        if (direction == TouchListener.MOVE_TO_LEFT) {
            currentTopEndIndex = currentShowEndIndex;
            final int nextIndex = currentBottomEndIndex + COUNT;
            currentShowEndIndex = currentBottomEndIndex;
            if (textLenght > nextIndex) {
                txt = text.substring(currentBottomEndIndex, nextIndex);
                currentBottomEndIndex = nextIndex;
            }
            else{
                txt = text.substring(currentBottomEndIndex, textLenght);
                currentBottomEndIndex = textLenght;
            }
        } else {
            currentBottomEndIndex = currentShowEndIndex;
            currentShowEndIndex = currentTopEndIndex;
            currentTopEndIndex = currentTopEndIndex - COUNT;
            txt = text.substring(currentTopEndIndex - COUNT, currentTopEndIndex);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.view_new, null);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(txt);
        return view;
    }

    @Override
    public boolean whetherHasPreviousPage() {
        return currentShowEndIndex > COUNT;
    }

    @Override
    public boolean whetherHasNextPage() {
        return currentShowEndIndex < textLenght;
    }

    @Override
    public boolean currentIsFirstPage() {
        boolean should = currentTopEndIndex > COUNT;
        if (!should) {
            currentBottomEndIndex = currentShowEndIndex;
            currentShowEndIndex = currentTopEndIndex;
            currentTopEndIndex = currentTopEndIndex - COUNT;
        }
        return should;
    }

    @Override
    public boolean currentIsLastPage() {
        boolean should = currentBottomEndIndex < textLenght;
        if (!should) {
            currentTopEndIndex = currentShowEndIndex;
            final int nextIndex = currentBottomEndIndex + COUNT;
            currentShowEndIndex = currentBottomEndIndex;
            if (textLenght > nextIndex) {
                currentBottomEndIndex = nextIndex;
            } else {
                currentBottomEndIndex = textLenght;
            }
        }
        return should;
    }

    @Override
    public void goToNextChapter(){
        position += 1;
        try{
            Toast.makeText(this,"正在加载下一章",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Reading.this,Reading.class);

            Bundle args = new Bundle();
            args.putSerializable("chapters",(Serializable)chapters);
            intent.putExtra("BUNDLE",args);
            intent.putExtra("position",position);
            startActivity(intent);
            finish();
        }
        catch (Exception e) {
            Toast.makeText(this,"已经到最后一章了",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRequestWithOkHttp() {
        final chapter toberead = (chapter) chapters.get(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String bianmaUrl = URLEncoder.encode(toberead.getLink(),"UTF-8");
                    Request request = new Request.Builder().url("https://novel.juhe.im/chapters/" + bianmaUrl).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    ChapterBean bean = gson.fromJson(responseData, ChapterBean.class);
                    text = toberead.getTitle() + "\n" + bean.getChapter().getCpContent().replaceAll("\\n+","                                                                        ");
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void hideBottomUIMenu(){
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 15 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }
}