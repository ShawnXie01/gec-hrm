package com.situ2001.hrm.pojo;

import java.util.Date;

public class Employee {
    private String qqNum;
    private String party;
    private Date birthday;
    private String race;
    private int state;
    private String speciality;
    private String hobby;
    private String tel;
    private String remark;
    private String postCode;
    private int id;
    private String name;
    private String cardId;
    private String address;
    private String phone;
    private String email;
    private int sex;
    private String education;
    private Date createDate;
    private int jobId;
    private int deptId;
    private String jobName;
    private String deptName;

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Employee() {
    }

    public Employee(String qqNum, String party, Date birthday, String race, String speciality, String hobby, String tel, String remark, String postCode, int id, String name, String cardId, String address, String phone, String email, int sex, String education, int jobId, int deptId) {
        this.qqNum = qqNum;
        this.party = party;
        this.birthday = birthday;
        this.race = race;
        this.speciality = speciality;
        this.hobby = hobby;
        this.tel = tel;
        this.remark = remark;
        this.postCode = postCode;
        this.id = id;
        this.name = name;
        this.cardId = cardId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.education = education;
        this.jobId = jobId;
        this.deptId = deptId;
    }

    public Employee(String name, String cardId, String address, String phone, String email, int sex, String education, Date createDate, int deptId, int jobId, String postCode, String qqNum, String party, Date birthday, String race, int state, String speciality, String hobby, String remark, String tel) {
        this.name = name;
        this.cardId = cardId;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.education = education;
        this.createDate = createDate;
        this.deptId = deptId;
        this.jobId = jobId;
        this.postCode = postCode;
        this.qqNum = qqNum;
        this.party = party;
        this.birthday = birthday;
        this.race = race;
        this.state = state;
        this.speciality = speciality;
        this.hobby = hobby;
        this.remark = remark;
        this.tel = tel;
    }

    public String getQqNum() {
        return qqNum;
    }

    public String getParty() {
        return party;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
