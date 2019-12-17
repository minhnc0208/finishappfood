package com.duanmot.myapplication.model;

public class PhanLoaiMonAn {
    private String mPLMA, thucDon, loaiMon, cachThucHien, mua, mucDich, maMonAn;
    public PhanLoaiMonAn() {
    }
    public PhanLoaiMonAn(String mPLMA, String thucDon, String loaiMon, String cachThucHien,
                         String mua, String mucDich, String maMonAn) {
        this.mPLMA = mPLMA;
        this.thucDon = thucDon;
        this.loaiMon = loaiMon;
        this.cachThucHien = cachThucHien;
        this.mua = mua;
        this.mucDich = mucDich;
        this.maMonAn = maMonAn;
    }
    public String getmPLMA() {
        return mPLMA;
    }

    public void setmPLMA(String mPLMA) {
        this.mPLMA = mPLMA;
    }

    public String getThucDon() {
        return thucDon;
    }

    public void setThucDon(String thucDon) {
        this.thucDon = thucDon;
    }

    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    public String getCachThucHien() {
        return cachThucHien;
    }

    public void setCachThucHien(String cachThucHien) {
        this.cachThucHien = cachThucHien;
    }

    public String getMua() {
        return mua;
    }

    public void setMua(String mua) {
        this.mua = mua;
    }

    public String getMucDich() {
        return mucDich;
    }

    public void setMucDich(String mucDich) {
        this.mucDich = mucDich;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }


}
