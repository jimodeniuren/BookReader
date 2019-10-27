package com.QQzzyy.bookreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class StringArrayAdapter extends RecyclerView.Adapter<StringArrayAdapter.ViewHolder>{
    private List<chapter> mSearchedBook;
    private Activity CONTEXT;
    private Intent intent;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView history;
        View bookView;
        public ViewHolder(View view){
            super(view);
            bookView = view;
            history = (TextView)view.findViewById(R.id.simple_text);
        }
    }

    public StringArrayAdapter(List<chapter> historyList, Activity context){
        this.mSearchedBook = historyList;
        intent = new Intent(context,Reading.class);
        this.CONTEXT = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final chapter s = mSearchedBook.get(position);
        holder.history.setText(s.getTitle());
        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("chapters",(Serializable)mSearchedBook);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("position",position);
                CONTEXT.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSearchedBook.size();
    }
}
