package com.example.drawerlayout;

public class DataProvider {

    private String no;
    private String nama;
    private String umur;
    private String pb;
    private String bb;
    private String status;

    public String getNo() {

        return no;
    }

    public void setNo(String no) {

        this.no = no;
    }

    public String getNama() {

        return nama;
    }

    public void setNama(String nama) {

        this.nama = nama;
    }

    public String getUmur() {

        return umur;
    }

    public void setUmur(String umur) {

        this.umur = umur;
    }

    public String getPb() {

        return pb;
    }

    public void setPb(String pb) {

        this.pb = pb;
    }

    public String getBb() {

        return bb;
    }

    public void setBb(String bb) {

        this.bb = bb;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public DataProvider(String no, String nama, String umur, String pb, String bb, String status) {

        this.no = no;
        this.nama = nama;
        this.umur = umur;
        this.pb = pb;
        this.bb = bb;
        this.status = status;
    }
}
