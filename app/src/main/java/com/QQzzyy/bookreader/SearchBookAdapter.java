package com.QQzzyy.bookreader;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder>{
    private List<SearchHistory> mSearchedBook;
    private Activity CONTEXT;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView history;

        public ViewHolder(View view){
            super(view);
            history = (TextView)view.findViewById(R.id.added_search_history);
        }
    }

    public SearchBookAdapter(List<SearchHistory> historyList,Activity context){
        this.mSearchedBook = historyList;
        this.CONTEXT = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SearchHistory s = mSearchedBook.get(position);
        holder.history.setText(s.getHistory());
    }

    @Override
    public int getItemCount() {
        return mSearchedBook.size();
    }
}
