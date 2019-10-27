package com.QQzzyy.bookreader;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private SwipedListener mAdapter;
    public ItemTouchHelperCallback(SwipedListener adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //设置拖拽方向为上下
        final int dragFlags= ItemTouchHelper.UP| ItemTouchHelper.DOWN;
        //设置侧滑方向为左右
        final int swipeFlags= ItemTouchHelper.START| ItemTouchHelper.END;
        //设置方向参数
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction== ItemTouchHelper.END) {//判断，如果Item滑动方向为右
            mAdapter.onItemRight(viewHolder.getAdapterPosition());
        }else if(direction== ItemTouchHelper.START){//判断，如果Item滑动方向为左
            mAdapter.onItemLift(viewHolder.getAdapterPosition());
        }
    }
}
