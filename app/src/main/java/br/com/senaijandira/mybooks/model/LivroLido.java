package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivroLido {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Livro.class, childColumns = "idLivro",parentColumns = "id")
    private int idLivro;

    @Ignore
    public LivroLido(){}

    public LivroLido(int idLivro){
        this.setIdLivro(idLivro);
    }

    //A imagem de capa é um array de bytes
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] capa;

    private String titulo;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
