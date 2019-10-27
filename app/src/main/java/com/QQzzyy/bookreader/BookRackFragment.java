package com.QQzzyy.bookreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class BookRackFragment extends Fragment {
    BookItemAdapter adapter;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.bookrackfragment,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<LikedBook> bL = DataSupport.findAll(LikedBook.class);
        List<Book> bookList = new ArrayList<>();
        for (LikedBook likedBook : bL)
            bookList.add(likedBook.backToBook());
        bL.clear();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rack_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =new BookItemAdapter(bookList,view.getContext());
        recyclerView.setAdapter(adapter);
    }
}
