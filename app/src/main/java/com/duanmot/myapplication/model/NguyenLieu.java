package com.duanmot.myapplication.model;



public class NguyenLieu {

    private String maNguyenLieu, tenNguyenLieu, maMonAn;


    public NguyenLieu() {
    }


    public NguyenLieu(String maNguyenLieu, String tenNguyenLieu, String maMonAn) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.maMonAn = maMonAn;
    }


    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }
}
