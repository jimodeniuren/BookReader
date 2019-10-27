package com.QQzzyy.bookreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class CommentFragment extends Fragment {
    private static final String TAG = "MainActivity";
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.commentfragment,container,false);
        intent = new Intent(MyApplication.getContext(),CommentList.class);
        NavigationView navigation_View = (NavigationView) view.findViewById(R.id.comment_fragment);
        navigation_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.zonghetaolunqu:
                        intent.putExtra("commentid","by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=50&distillate=");
                        startActivity(intent);
                        break;
                    case R.id.shuhuanghuzhuqu:
                        intent.putExtra("commentid","help?duration=all&sort=updated&start=0&limit=50&distillate=");
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        return view;
    }
}
