package com.jnu.ITime;


import java.io.Serializable;
import java.util.Date;



public class Event implements Serializable {

    private int id;
    private String Content;
    private Date endDate;
    private Date startDate;
    private int ImageId;
    private Long dateCounts;

    public Event(int id,String Content, Date endDate, Date startDate, int ImageId) {
        this.id = id;
        this.Content = Content;
        this.endDate = endDate;
        this.startDate = startDate;
        this.ImageId = ImageId;
        this.dateCounts = CalculateTime.getDateInterval(this.endDate);
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Date getendDate() {
        return this.endDate;
    }

    public void setendDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getstartDate() {
        return this.startDate;
    }

    public void setstartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public long getDateCounts() {
        return dateCounts;
    }

    public void setDateCounts(Long dateCounts) {
        this.dateCounts = dateCounts;
    }
}
