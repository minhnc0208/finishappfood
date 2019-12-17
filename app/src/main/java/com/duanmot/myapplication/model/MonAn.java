package com.duanmot.myapplication.model;

public class MonAn {

    private String maMonAn, tenMonAn, anhMonAn, videoMonAn, moTa, thoiGianChuanBi, thoiGianThucHien, doKho,
            khauPhan, maNguoiDung, thoiGianTaoMonAn;

    public MonAn() { }

    public MonAn(String maMonAn, String tenMonAn, String anhMonAn, String videoMonAn, String moTa, String thoiGianChuanBi,
                 String thoiGianThucHien, String doKho, String khauPhan, String maNguoiDung, String thoiGianTaoMonAn) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.anhMonAn = anhMonAn;
        this.videoMonAn = videoMonAn;
        this.moTa = moTa;
        this.thoiGianChuanBi = thoiGianChuanBi;
        this.thoiGianThucHien = thoiGianThucHien;
        this.doKho = doKho;
        this.khauPhan = khauPhan;
        this.maNguoiDung = maNguoiDung;
        this.thoiGianTaoMonAn = thoiGianTaoMonAn;
    }

    public MonAn(String tenMonAn, String anhMonAn, String videoMonAn, String moTa, String thoiGianChuanBi,
                 String thoiGianThucHien, String doKho, String khauPhan) {
        this.tenMonAn = tenMonAn;
        this.anhMonAn = anhMonAn;
        this.videoMonAn = videoMonAn;
        this.moTa = moTa;
        this.thoiGianChuanBi = thoiGianChuanBi;
        this.thoiGianThucHien = thoiGianThucHien;
        this.doKho = doKho;
        this.khauPhan = khauPhan;
    }

    public MonAn(String maMonAn, String tenMonAn, String anhMonAn, String videoMonAn, String moTa, String thoiGianChuanBi,
                 String thoiGianThucHien, String doKho, String khauPhan) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.anhMonAn = anhMonAn;
        this.videoMonAn = videoMonAn;
        this.moTa = moTa;
        this.thoiGianChuanBi = thoiGianChuanBi;
        this.thoiGianThucHien = thoiGianThucHien;
        this.doKho = doKho;
        this.khauPhan = khauPhan;
    }

    public MonAn(String maMonAn, String tenMonAn, String anhMonAn, String videoMonAn, String moTa, String thoiGianChuanBi,
                 String thoiGianThucHien, String doKho, String khauPhan, String maNguoiDung) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.anhMonAn = anhMonAn;
        this.videoMonAn = videoMonAn;
        this.moTa = moTa;
        this.thoiGianChuanBi = thoiGianChuanBi;
        this.thoiGianThucHien = thoiGianThucHien;
        this.doKho = doKho;
        this.khauPhan = khauPhan;
        this.maNguoiDung = maNguoiDung;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getAnhMonAn() {
        return anhMonAn;
    }

    public void setAnhMonAn(String anhMonAn) {
        this.anhMonAn = anhMonAn;
    }

    public String getVideoMonAn() {
        return videoMonAn;
    }

    public void setVideoMonAn(String videoMonAn) {
        this.videoMonAn = videoMonAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThoiGianChuanBi() {
        return thoiGianChuanBi;
    }

    public void setThoiGianChuanBi(String thoiGianChuanBi) {
        this.thoiGianChuanBi = thoiGianChuanBi;
    }

    public String getThoiGianThucHien() {
        return thoiGianThucHien;
    }

    public void setThoiGianThucHien(String thoiGianThucHien) {
        this.thoiGianThucHien = thoiGianThucHien;
    }

    public String getDoKho() {
        return doKho;
    }

    public void setDoKho(String doKho) {
        this.doKho = doKho;
    }

    public String getKhauPhan() {
        return khauPhan;
    }

    public void setKhauPhan(String khauPhan) {
        this.khauPhan = khauPhan;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getThoiGianTaoMonAn() {
        return thoiGianTaoMonAn;
    }

    public void setThoiGianTaoMonAn(String thoiGianTaoMonAn) {
        this.thoiGianTaoMonAn = thoiGianTaoMonAn;
    }
}
