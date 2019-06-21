package model;

import java.sql.Date;

public class Video {
    private String title;
    private int id;
    private Date dataCriacao;

    public Video(String titulo) {
        this.title = titulo;
    }

    public Video() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
