package com.example.teoriinformasi.Adapter;

import java.io.Serializable;

public class Request implements Serializable {
    private String symbol, binary, jumlah, kraft;

    public Request() {
    }

    public Request(String symbol, String binary, String jumlah, String kraft) {
        this.symbol = symbol;
        this.binary = binary;
        this.jumlah = jumlah;
        this.kraft = kraft;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKraft() {
        return kraft;
    }

    public void setKraft(String kraft) {
        this.kraft = kraft;
    }

    @Override
    public String toString() {
        return "Request{" +
                "symbol='" + symbol + '\'' +
                ", binary='" + binary + '\'' +
                ", jumlah='" + jumlah + '\'' +
                ", kraft='" + kraft + '\'' +
                '}';
    }
}
