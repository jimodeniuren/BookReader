package com.QQzzyy.bookreader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class RankingListFragment extends Fragment {
    private static final String TAG = "MainActivity";
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rankinglistfragment, container, false);
        intent = new Intent(MyApplication.getContext(),RankingList.class);
        NavigationView navigation_View = (NavigationView) view.findViewById(R.id.book_ranking_list);
        navigation_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.resoubang:
                        intent.putExtra("rankid","5a6844f8fc84c2b8efaa8bc5");
                        startActivity(intent);
                        break;
                    case R.id.changxiaobang:
                        intent.putExtra("rankid","5a39ca20fc84c2b8ef82c9ed");
                        startActivity(intent);
                        break;
                    case R.id.vipbang:
                        intent.putExtra("rankid","5a683b68fc84c2b8efa68fc2");
                        startActivity(intent);
                        break;
                    case R.id.xinshubang:
                        intent.putExtra("rankid","5a39ca3ffc84c2b8ef82da82");
                        startActivity(intent);
                        break;
                    case R.id.renqibang:
                        intent.putExtra("rankid","5a322ef4fc84c2b8efaa8335");
                        startActivity(intent);
                        break;
                    case R.id.rexiaobang:
                        intent.putExtra("rankid","5a68296bfc84c2b8ef9efdb0");
                        startActivity(intent);
                        break;
                    case R.id.wanjiebang:
                        intent.putExtra("rankid","5a39ca59fc84c2b8ef82e96c");
                        startActivity(intent);
                        break;
                    case R.id.zhuishuzuirebangtop100:
                        intent.putExtra("rankid","54d42d92321052167dfb75e3");
                        startActivity(intent);
                        break;
                    case R.id.haopingbang:
                        intent.putExtra("rankid","5a6844aafc84c2b8efaa6b6e");
                        startActivity(intent);
                        break;
                    case R.id.qianlibang:
                        intent.putExtra("rankid","54d42e72d9de23382e6877fb");
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        return view;
    }
}
