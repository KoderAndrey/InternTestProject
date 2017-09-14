package com.example.interntestproject;

public class ObjectUserDataBase {
    private long mUsId;
    private String mFirstName;
    private String mLastName;
    private String mDate;
    public ObjectUserDataBase(long mUsId, String mFirstName, String mLastName, String mDate)
    {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mDate = mDate;
        this.mUsId = mUsId;
    }
    public ObjectUserDataBase()
    {}
    public void setFirstName(String mFirstName)
    {
        this.mFirstName = mFirstName;
    }
    public String getFirstName()
    {
        return mFirstName;
    }
    public void setLastName(String mLastName)
    {
        this.mLastName = mLastName;
    }
    public String getLastName()
    {
        return mLastName;
    }
    public void setDate(String mDate)
    {
        this.mDate = mDate;
    }
    public String getDate()
    {
        return mDate;
    }
    public void setUsId(long mUsId)
    {
        this.mUsId = mUsId;
    }
    public long getUsId() {
        return mUsId;
    }

}
