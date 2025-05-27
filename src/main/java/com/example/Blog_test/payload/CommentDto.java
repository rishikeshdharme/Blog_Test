package com.example.Blog_test.payload;

public class CommentDto {

    private Integer cid;
    private String content;
    private String username;

    public CommentDto(Integer cid, String content,String username) {
        this.cid = cid;
        this.content = content;
        this.username = username;
    }

    public CommentDto() {
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
