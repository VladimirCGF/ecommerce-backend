package br.com.ecommerce.relogios.form;


import jakarta.ws.rs.FormParam;

import java.io.InputStream;

public class WatchImageForm {

    @FormParam("nomeImagem")
    private String nameImage;

    @FormParam("imagem")
    public InputStream imagem;


    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public InputStream getImagem() {
        return imagem;
    }

    public void setImagem(InputStream imagem) {
        this.imagem = imagem;
    }
}
