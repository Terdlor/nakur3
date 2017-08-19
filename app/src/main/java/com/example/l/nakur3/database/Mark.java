package com.example.l.nakur3.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by L on 15.08.2017.
 */
@DatabaseTable(tableName = "mark")
public class Mark {
    public Mark(){

    }
    @DatabaseField(generatedId = true)
    protected  int id;
    @DatabaseField(canBeNull = false)
    protected  String name;
    @DatabaseField
    protected  String status;
    @DatabaseField
    protected Date addDate;
    @DatabaseField
    protected Date synDate;
    @DatabaseField
    protected String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
