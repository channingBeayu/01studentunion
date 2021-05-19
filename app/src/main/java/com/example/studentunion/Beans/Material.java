package com.example.studentunion.Beans;

public class Material {
    private Integer _id;
    private String materialName;
    private String num;
    private String position;

    public Material() {
    }

    public Material(Integer _id, String materialName, String num, String position) {
        this._id = _id;
        this.materialName = materialName;
        this.num = num;
        this.position = position;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Material{" +
                "_id=" + _id +
                ", materialName='" + materialName + '\'' +
                ", num='" + num + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
