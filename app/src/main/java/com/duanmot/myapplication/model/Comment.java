package com.duanmot.myapplication.model;

import com.google.firebase.database.ServerValue;

public class Comment {

    private String ucomment,uid,uimg,uname,time, commentCount;

    public Comment() {
    }

    public Comment(String ucomment, String uid, String uimg, String uname, String time) {
        this.ucomment = ucomment;
        this.uid = uid;
        this.uimg = uimg;
        this.uname = uname;
        this.time = time;
    }


    public String getUcomment() {
        return ucomment;
    }

    public void setUcomment(String ucomment) {
        this.ucomment = ucomment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
