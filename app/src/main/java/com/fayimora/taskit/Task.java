package com.fayimora.taskit;

import java.util.Date;

/**
 * Created by fayimora on 01/06/15.
 */
public class Task {
    private String mName;
    private Date mDueDate;
    private boolean mDone;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public String toString(){
        return mName;
    }
}
