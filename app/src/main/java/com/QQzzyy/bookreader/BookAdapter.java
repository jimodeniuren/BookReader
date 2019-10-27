package com.QQzzyy.bookreader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements SwipedListener
{
    private static final String TAG = "MainActivity";
    private List<Book> mBookList;
    private Intent intent;
    private Activity CONTEXT;
    private String url;

    @Override
    public void onItemRight(int position) {
        mBookList.get(position).delete();
        mBookList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemLift(int position) {
        mBookList.get(position).delete();
        mBookList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View bookView;
        ImageView bookCover;
        TextView bookName;

        public ViewHolder(View view)
        {
            super(view);
            bookView = view;
            bookCover = (ImageView) view.findViewById(R.id.book_cover);
            bookName = (TextView) view.findViewById(R.id.book_name);
        }
    }
    public BookAdapter(List<Book> bookList, Activity context){
        mBookList = bookList;
        intent = new Intent(context,BookIntroduction.class);
        CONTEXT = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = mBookList.get(position);
        Glide.with(holder.bookView).load("https://statics.zhuishushenqi.com" + book.getCover()).into(holder.bookCover);
        holder.bookName.setText(book.getTitle());
        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book",book);
                CONTEXT.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
