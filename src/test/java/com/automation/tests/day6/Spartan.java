package com.automation.tests.day6;


import com.google.gson.annotations.SerializedName;

// {
//        "id": 147,
//        "name": "Uyghurjon",
//        "gender": "Male",
//        "phone": 12023615117
//    }
public class Spartan {
    @SerializedName("id")
    private int spartanId;
    private String name;
    private String gender;
    private Long phone;

    public Spartan(int spartanId, String name, String gender, Long phone) {
        this.spartanId = spartanId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public int getSpartanId() {
        return spartanId;
    }

    public void setSpartanId(int spartanId) {
        this.spartanId = spartanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "spartanId=" + spartanId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }

    public Spartan() {
    }
}
