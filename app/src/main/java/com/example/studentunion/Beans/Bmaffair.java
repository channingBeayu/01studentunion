package com.example.studentunion.Beans;



public class Bmaffair {
    private Integer _id;
    private String bmaffairName;
    private Integer process;
    private String department;

    public Bmaffair() {
    }

    public Bmaffair(Integer _id, String bmaffairName, Integer process, String department) {
        this._id = _id;
        this.bmaffairName = bmaffairName;
        this.process = process;
        this.department = department;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getBmaffairName() {
        return bmaffairName;
    }

    public void setBmaffairName(String bmaffairName) {
        this.bmaffairName = bmaffairName;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Bmaffair{" +
                "_id=" + _id +
                ", bmaffairName='" + bmaffairName + '\'' +
                ", process='" + process + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
