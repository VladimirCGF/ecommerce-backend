package br.com.ecommerce.relogios.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.*;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    private static final String BASE_DIRECTORY = "C:/Users/vladi/OneDrive/Documentos/FACULDADE 5P/TOPICOS EM PROGRAMACAO II/TRABALHO/ecommerce-backend/src/main/resources/images/";


    @Override
    public InputStream download(Long id, String nomeImagem) throws IOException {
        // Construir o caminho com o diretório do id
        File file = new File(BASE_DIRECTORY + id + "/" + nomeImagem);
        if (!file.exists()) {
            throw new IOException("Imagem não encontrada: " + nomeImagem);
        }
        return new FileInputStream(file);
    }

    @Override
    public File save(Long id, String nomeImagem, InputStream imagem) throws IOException {
        File directory = new File(BASE_DIRECTORY + id);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Diretório criado: " + created);
        }

        File file = new File(directory, nomeImagem);
        System.out.println("Salvando arquivo em: " + file.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imagem.readAllBytes());
            System.out.println("Arquivo salvo com sucesso!");
            return file;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            throw e;
        }
    }

}
