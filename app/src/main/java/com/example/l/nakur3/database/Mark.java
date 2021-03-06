package com.example.l.nakur3.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by L on 15.08.2017.
 */
@DatabaseTable(tableName = "mark")
public class Mark {
    public Mark(){
      flavorList = new ArrayList<>();
    }

    @DatabaseField(generatedId = true)
    protected  int id;
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

    private List<Flavor> flavorList;
    public  void addFlavorList(Flavor flavor){
        flavorList.add(flavor);
    }
    public  void  removeFlavorList(Flavor flavor){
        flavorList.remove(flavor);
    }


    @ForeignCollectionField(eager = true)
    private Collection<Flavor> flavorListDB;

    public void addFlavorDB(Flavor flavor)  throws SQLException {
        HelperFactory.getHelper().getFlavorDAO().create(flavor);
        flavorListDB.add(flavor);
    }

    public void  removeFlavorDB(Flavor flavor) throws  SQLException{
        flavorListDB.remove(flavor);
        HelperFactory.getHelper().getFlavorDAO().delete(flavor);
    }
    public  List<Flavor> getFlavorListDB(){
        return  new ArrayList<Flavor>(flavorListDB);
    }

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

    public int getIdDB() {
        return idDB;
    }

    public void setIdDB(int idDB) {
        this.idDB = idDB;
    }
}
