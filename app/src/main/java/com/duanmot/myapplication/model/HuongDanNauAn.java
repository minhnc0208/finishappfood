package com.duanmot.myapplication.model;

public class HuongDanNauAn {

    private String maHDNA, noiDung, maMonAn;

    public HuongDanNauAn() {
    }

    public HuongDanNauAn(String maHDNA, String noiDung, String maMonAn) {
        this.maHDNA = maHDNA;
        this.noiDung = noiDung;
        this.maMonAn = maMonAn;
    }

    public String getMaHDNA() {
        return maHDNA;
    }

    public void setMaHDNA(String maHDNA) {
        this.maHDNA = maHDNA;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }
}
