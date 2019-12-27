package com.darko.mynotesapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;

    //NO-ARGS CONSTRUCTOR
    @Ignore
    public Note() {
    }

    //CONSTRUCTOR WHEN WE WANT TO EDIT AN EXISTING NOTE
    @Ignore
    public Note(Date date, String text) {
        this.date = date;
        this.text = text;
    }

    //CUSTOM CONSTRUCTOR THAT WE WANT TO USE WITH ROOM
    public Note(int id, Date date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}