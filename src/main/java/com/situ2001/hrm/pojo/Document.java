package com.situ2001.hrm.pojo;

import java.util.Date;

public class Document {
    private int id;
    private String title;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String remark;
    private Date createDate;
    private int userId;
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
