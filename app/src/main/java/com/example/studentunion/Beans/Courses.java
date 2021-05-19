package com.example.studentunion.Beans;

import java.util.Arrays;

public class Courses {
    private String userName;
    private String stuNum;
    private boolean[][] courseArray;

    public Courses() {
    }

    public Courses(String userName, String stuNum, boolean[][] courses) {
        this.userName = userName;
        this.stuNum = stuNum;
        this.courseArray = courses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public boolean[][] getcourseArray() {
        return courseArray;
    }

    public void setcourseArray(boolean[][] courses) {
        this.courseArray = courses;
    }

}
