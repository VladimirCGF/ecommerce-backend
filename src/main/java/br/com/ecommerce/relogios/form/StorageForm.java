package br.com.ecommerce.relogios.form;

import jakarta.ws.rs.FormParam;

import java.io.InputStream;

public class StorageForm {

    @FormParam("idWatch")
    private Long idWatch;

    @FormParam("nameImage")
    private String name;

    @FormParam("imagem")
    public InputStream imagem;

    public Long getIdWatch() {
        return idWatch;
    }

    public void setIdWatch(Long idWatch) {
        this.idWatch = idWatch;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameImage) {
        this.name = nameImage;
    }

    public InputStream getImagem() {
        return imagem;
    }

    public void setImagem(InputStream imagem) {
        this.imagem = imagem;
    }
}
