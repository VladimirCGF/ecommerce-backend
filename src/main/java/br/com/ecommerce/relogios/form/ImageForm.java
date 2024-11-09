package br.com.ecommerce.relogios.form;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class ImageForm {

    @FormParam("nomeImagem")
    @PartType(MediaType.TEXT_PLAIN)
    private String nameImage;

    @FormParam("imagem")
    @PartType("application/octet-stream")
    private byte[] image;

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
