package com.QQzzyy.bookreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchBook extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<SearchHistory> historyList= new ArrayList<>();
    private SearchBookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        init();
    }

    private void init(){
        historyList = DataSupport.findAll(SearchHistory.class);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.searchbookhistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchBook.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SearchBookAdapter(historyList,SearchBook.this);
        recyclerView.setAdapter(adapter);
        Button searchitem = (Button)findViewById(R.id.startsearchbook);
        searchitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText enter = (EditText)findViewById(R.id.tobesearchedbook);
                try{
                    String entered = enter.getText().toString();
                    if (entered.equals(""))
                        throw new Exception();
                    SearchHistory sh = new SearchHistory();
                    sh.setHistory(entered);
                    sh.save();
                    Intent intent = new Intent(SearchBook.this,SearchResult.class);
                    intent.putExtra("tobesearched",entered);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(SearchBook.this,"请输入要搜索的书名！",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button clearall = findViewById(R.id.clearall);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(SearchHistory.class);
                historyList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
