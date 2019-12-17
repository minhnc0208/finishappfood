package com.duanmot.myapplication.model;

public class RatingStar {

    String idRatingStar, idUser, idMonAn;
    double ratingStar;

    public RatingStar() {
    }

    public RatingStar(String idUser, float ratingStar) {
        this.idUser = idUser;
        this.ratingStar = ratingStar;
    }

    public RatingStar( String idUser,String idMonAn, float ratingStar) {
        this.idUser = idUser;
        this.idMonAn = idMonAn;
        this.ratingStar = ratingStar;
    }

    public RatingStar(String idRatingStar, String idUser, String idMonAn, float ratingStar) {
        this.idRatingStar = idRatingStar;
        this.idUser = idUser;
        this.idMonAn = idMonAn;
        this.ratingStar = ratingStar;
    }

    public String getIdRatingStar() {
        return idRatingStar;
    }

    public void setIdRatingStar(String idRatingStar) {
        this.idRatingStar = idRatingStar;
    }

    public String getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(String idMonAn) {
        this.idMonAn = idMonAn;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public double getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(double ratingStar) {
        this.ratingStar = ratingStar;
    }
}
