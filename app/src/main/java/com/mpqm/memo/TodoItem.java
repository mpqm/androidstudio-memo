package com.mpqm.memo;


public class TodoItem {
    private int id; //메모 고유 id
    private String title; //메모제목
    private String content; //메모내용
    private String writeDate; //메모작성날짜

    //    public Todoitem(int id, String title, String content, String writeDate) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.writeDate = writeDate;
//    }
    public TodoItem(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
