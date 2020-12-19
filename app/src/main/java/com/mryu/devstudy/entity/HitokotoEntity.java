package com.mryu.devstudy.entity;

public class HitokotoEntity {
    /**
     * id : 4783
     * uuid : b8b3e14b-1a81-402a-864a-d137eb0f60e7
     * hitokoto : 雪崩时，没有一片雪花是无辜的！
     * type : f
     * from : 网络
     * from_who : null
     * creator : M&Yu
     * creator_uid : 4055
     * reviewer : 5921
     * commit_from : web
     * created_at : 1571143787
     * length : 15
     */

    private int id;
    private String uuid;
    private String hitokoto;
    private String type;
    private String from;
    private Object from_who;
    private String creator;
    private int creator_uid;
    private int reviewer;
    private String commit_from;
    private String created_at;
    private int length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Object getFrom_who() {
        return from_who;
    }

    public void setFrom_who(Object from_who) {
        this.from_who = from_who;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getCreator_uid() {
        return creator_uid;
    }

    public void setCreator_uid(int creator_uid) {
        this.creator_uid = creator_uid;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommit_from() {
        return commit_from;
    }

    public void setCommit_from(String commit_from) {
        this.commit_from = commit_from;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
