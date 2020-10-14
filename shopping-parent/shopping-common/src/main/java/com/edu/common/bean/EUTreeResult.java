package com.edu.common.bean;

public class EUTreeResult {
    private Long id;
    private String text;
    private String state; // 'open' 或 'closed'
    // fn +alt +insert
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
