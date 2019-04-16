package com.ferraris.premierprojetcourandroid.model.beans;

import java.util.ArrayList;


public class ResultBean {


    private ArrayList<CityBean> results;
    private int nbr;
    private ErrorBean errors;

    public ResultBean() {
    }

    public ResultBean(ArrayList<CityBean> results, int nbr) {
        this.results = results;
        this.nbr = nbr;
    }

    public ResultBean(ErrorBean errors) {
        this.errors = errors;
    }


    public ResultBean(ArrayList<CityBean> results, int nbr, ErrorBean errors) {
        this.results = results;
        this.nbr = nbr;
        this.errors = errors;
    }

    @Override
    public String toString() {

        return "";
    }

    public ArrayList<CityBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<CityBean> results) {
        this.results = results;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public ErrorBean getErrors() {
        return errors;
    }

    public void setErrors(ErrorBean errors) {
        this.errors = errors;
    }
}

