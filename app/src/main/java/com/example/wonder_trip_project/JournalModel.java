package com.example.wonder_trip_project;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class JournalModel {

    private String title;
    private String date;
    private String rate;
    private String journal;
    private String journalId;
    private String srcImageJournal;

    public JournalModel() {
    }

    public JournalModel(String title, String date, String rate) {
        this.title = title;
        this.date = date;
        this.rate = rate;
    }

    public JournalModel(String title, String date, String rate, String journalId) {
        this(title, date, rate);
        this.journalId = journalId;
    }

    public JournalModel(String title, String date, String rate,String journalId, String srcImageJournal) {
        this(title, date, rate, journalId);
        this.srcImageJournal = srcImageJournal;
    }

    public String getSrcImageJournal() {
        return srcImageJournal;
    }

    public void setSrcImageJournal(String srcImageJournal) {
        this.srcImageJournal = srcImageJournal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }
}
