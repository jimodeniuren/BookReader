package com.QQzzyy.bookreader;

import org.litepal.crud.DataSupport;

public class SearchHistory extends DataSupport {
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
