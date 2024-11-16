package br.com.ecommerce.relogios.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    File save(Long id, String nameImage, InputStream image) throws IOException;

    InputStream download(Long id, String nomeImagem) throws IOException;
}
