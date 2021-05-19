package com.example.studentunion.Beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Student  implements Parcelable {
    private Integer _id;
    private String userName;
    private String stuNum;
    private String password;
    private String gender;
    private String phone;
    private String department;
    private String className;


    public Student(Integer _id, String userName, String stuNum, String password, String gender, String phone, String department, String className) {
        this._id = _id;
        this.userName = userName;
        this.stuNum = stuNum;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.department = department;
        this.className = className;
    }

    public Student() {
    }

    protected Student(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readInt();
        }
        userName = in.readString();
        stuNum = in.readString();
        password = in.readString();
        gender = in.readString();
        phone = in.readString();
        department = in.readString();
        className = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(_id);
        }
        dest.writeString(userName);
        dest.writeString(stuNum);
        dest.writeString(password);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(department);
        dest.writeString(className);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id='" + _id + '\'' +
                ", userName='" + userName + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
