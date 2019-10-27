package com.QQzzyy.bookreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{
    private static final String TAG = "MainActivity";
    private List<post> mPostList;
    private Intent intent;
    private Context CONTEXT;
    private String url;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View postView;
        CircleImageView commentorAvator;
        TextView commentorName;
        TextView commentorNeirong;

        public ViewHolder(View view){
            super(view);
            postView = view;
            commentorAvator = (CircleImageView) view.findViewById(R.id.commentor_avator);
            commentorName = (TextView)view.findViewById(R.id.commentor_name);
            commentorNeirong = (TextView)view.findViewById(R.id.commentor_neirong);
        }
    }

    public CommentListAdapter(List<post> posts, Context context){
        mPostList = posts;
        intent = new Intent(context,CommentDetailed.class);
        CONTEXT = context;
    }

    @NonNull
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentitem,parent,false);
        final CommentListAdapter.ViewHolder holder = new CommentListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentListAdapter.ViewHolder holder, int position) {
        final post post = mPostList.get(position);
        holder.commentorName.setText(post.getAuthor().getNickname());
        holder.commentorNeirong.setText(post.getTitle());
        Glide.with(holder.postView).load("https://statics.zhuishushenqi.com" + post.getAuthor().getAvatar()).into(holder.commentorAvator);
        holder.postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("post",post);
                CONTEXT.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
