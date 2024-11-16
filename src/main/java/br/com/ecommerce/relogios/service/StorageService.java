package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.model.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface StorageService {

    public List<Storage> findAll();

    public Storage findById(Long id);

    public Storage create(Storage dto, InputStream imagem) throws IOException;

    public void update(Long id, Storage dto);

    public void delete(Long id);

}
