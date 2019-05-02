package br.org.catolicasc.leitorrss;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private String id;
    private String nome;
    private String imgUrl;
    private String numero;
    private String altura;
    private String peso;

    public Pokemon(String id, String name, String img, String numero, String altura, String peso) {
        this.id = id;
        this.nome = name;
        this.imgUrl = img;
        this.numero = numero;
        this.altura = altura;
        this.peso = peso;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public String getImgUrl() {
        return imgUrl;
    }


    @Override
    public String toString() {
        return  "id=" + id + '\n' +
                "nome=" + nome + '\n' +
                ", urlImagem=" + imgUrl + '\n';
    }

}
