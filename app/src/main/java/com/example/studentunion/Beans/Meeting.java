package com.example.studentunion.Beans;

public class Meeting {
    private Integer _id;
    private String theme;
    private String time;
    private String type;
    private String brief;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreif() {
        return brief;
    }

    public void setBreif(String breif) {
        this.brief = breif;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "_id=" + _id +
                ", theme='" + theme + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", breif='" + brief + '\'' +
                '}';
    }
}
