package br.com.ecommerce.relogios.model;

import br.com.ecommerce.relogios.dto.WatchDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.File;

public class WatchUploadForm {

    @FormParam("watchDTO")
    @Valid
    private WatchDTO watchDTO;

    @FormParam("imageFile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private File imageFile;

    public @Valid WatchDTO getWatchDTO() {
        return watchDTO;
    }

    public void setWatchDTO(@Valid WatchDTO watchDTO) {
        this.watchDTO = watchDTO;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
