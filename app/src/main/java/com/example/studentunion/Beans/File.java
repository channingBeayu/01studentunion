package com.example.studentunion.Beans;


import android.os.Parcel;
import android.os.Parcelable;

public class File implements Parcelable {
    private Integer _id;
    private String fileName;
    private String groupchat;
    private String department;

    public File() {
    }

    public File(Integer _id, String fileName, String groupchat, String department) {
        this._id = _id;
        this.fileName = fileName;
        this.groupchat = groupchat;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getGroupchat() {
        return groupchat;
    }

    public void setGroupchat(String groupchat) {
        this.groupchat = groupchat;
    }

    @Override
    public String toString() {
        return "File{" +
                "_id=" + _id +
                ", fileName='" + fileName + '\'' +
                ", groupchat='" + groupchat + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
    protected File(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readInt();
        }
        fileName = in.readString();
        groupchat = in.readString();
        department = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(_id);
        }
        dest.writeString(fileName);
        dest.writeString(groupchat);
        dest.writeString(department);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };
}
