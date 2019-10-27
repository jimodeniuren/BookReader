package com.QQzzyy.bookreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder>{
    private static final String TAG = "MainActivity";
    private List<Book> mBookItemList;
    private Intent intent;
    private Context CONTEXT;
    private String url;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View bookView;
        ImageView bookCover;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookShortIntroduction;

        public ViewHolder(View view){
            super(view);
            bookView = view;
            bookCover = (ImageView)view.findViewById(R.id.bookitem_cover);
            bookTitle = (TextView)view.findViewById(R.id.bookitem_title);
            bookAuthor = (TextView)view.findViewById(R.id.bookitem_author);
            bookShortIntroduction = (TextView)view.findViewById(R.id.bookitem_short_introduction);
        }
    }

    public BookItemAdapter(List<Book> bookList, Context context){
        mBookItemList = bookList;
        intent = new Intent(context,BookIntroduction.class);
        CONTEXT = context;
    }

    @NonNull
    @Override
    public BookItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookitem,parent,false);
        final BookItemAdapter.ViewHolder holder = new BookItemAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookItemAdapter.ViewHolder holder, int position) {
        final Book book = mBookItemList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookShortIntroduction.setText(book.getShortIntro());
        Glide.with(holder.bookView).load("https://statics.zhuishushenqi.com" + book.getCover()).into(holder.bookCover);
        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book",book);
                if (DataSupport.where("bookId = ?",book.get_id()).find(Book.class).size() == 0)
                    book.save();
                CONTEXT.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookItemList.size();
    }
}
