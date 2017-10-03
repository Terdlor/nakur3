package com.example.l.nakur3.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by L on 15.08.2017.
 */
@DatabaseTable(tableName = "flavor")
public class Flavor {
    public Flavor(){

    }
    @DatabaseField(generatedId = true)
    protected  int id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected Mark idMark;
    @DatabaseField
    protected  int idDB;
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

   /* public int getIdMark() {
        return idMark;
    }

    public void setIdMark(int idMark) {
        this.idMark = idMark;
    }*/

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

    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }
}
