package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.WatchDTO;
import br.com.ecommerce.relogios.dto.WatchResponseDTO;
import br.com.ecommerce.relogios.model.Storage;
import br.com.ecommerce.relogios.model.Watch;
import br.com.ecommerce.relogios.repository.WatchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class WatchServiceImpl implements WatchService {

    @Inject
    WatchRepository watchRepository;

    @Inject
    FileService fileService;

    private static final String IMAGE_UPLOAD_DIR = "src/main/resources/static/assets/watch/";

    private static final String UPLOAD_DIR = "src/main/resources/images";

    @Transactional
    @Override
    public List<WatchResponseDTO> findAll() {
        return watchRepository
                .listAll()
                .stream()
                .map(WatchResponseDTO::valueOf).toList();
    }

    @Override
    public WatchResponseDTO findWatchById(Long id) {
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        return WatchResponseDTO.valueOf(watchRepository.findById(id));
    }

    @Transactional
    @Override
    public WatchResponseDTO create(WatchDTO watchDTO) {
        Watch watch = new Watch();
        watch.setName(watchDTO.name());
        watch.setDescription(watchDTO.description());
        watch.setPrice(watchDTO.price());
        watch.setMaterial(watchDTO.material());
        watch.setColor(watchDTO.color());
        watch.setGender(watchDTO.gender());
        watch.setBrand(watchDTO.brand());
        watch.setFormat(watchDTO.format());
        watch.setMechanism(watchDTO.mechanism());
        Storage storage = new Storage();
        watchRepository.persist(watch);
        return WatchResponseDTO.valueOf(watch);
    }

    @Transactional
    @Override
    public void update(Long id, WatchDTO watchDTO) {
        Watch watch = watchRepository.findById(id);
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        watch.setName(watchDTO.name());
        watch.setDescription(watchDTO.description());
        watch.setPrice(watchDTO.price());
        watch.setMaterial(watchDTO.material());
        watch.setColor(watchDTO.color());
        watch.setGender(watchDTO.gender());
        watch.setBrand(watchDTO.brand());
        watch.setFormat(watchDTO.format());
        watch.setMechanism(watchDTO.mechanism());
        watchRepository.persist(watch);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (watchRepository.findById(id) == null) {
            throw new NotFoundException("id watch not found");
        }
        watchRepository.deleteById(id);
    }

//    @Transactional
//    @Override
//    public List<String> getImageUrlsById(Long id) {
//        List<String> imageUrls = new ArrayList<>();
//        Watch watch = watchRepository.findById(id);
//        if (watch == null) {
//            throw new NotFoundException("id watch not found");
//        }
//        imageUrls = watch.getImageUrls().stream().toList();
//        return imageUrls;
//    }

    @Transactional
    @Override
    public WatchResponseDTO saveImagePerfil(Long id, String nameImage) {
        Watch watch = watchRepository.findById(id);
        watch.setImagePerfil(nameImage);

        return WatchResponseDTO.valueOf(watch);
    }

//    @Transactional
//    @Override
//    public void saveImageNamesFromDirectory(Long id) throws IOException {
//        // Buscar o relógio pelo ID
//        Watch watch = watchRepository.findById(id);
//        if (watch == null) {
//            throw new NotFoundException("Watch não encontrado com o ID: " + id);
//        }
//
//        // Caminho do diretório de imagens baseado no ID do relógio
//        Path imageDirectory = Paths.get("src/main/resources/images/" + id);
//
//        // Verificar se o diretório existe
//        if (!Files.exists(imageDirectory) || !Files.isDirectory(imageDirectory)) {
//            throw new NotFoundException("Diretório de imagens não encontrado para o Watch com ID: " + id);
//        }
//
//        // Obter a lista atual de nomes de imagens (ou inicializar uma lista vazia se for nulo)
//        List<String> imageNames = new ArrayList<>(watch.getImageUrls() != null ? watch.getImageUrls() : new ArrayList<>());
//
//        // Listar os arquivos de imagem no diretório e adicionar seus nomes, evitando duplicatas
//        try (Stream<Path> paths = Files.list(imageDirectory)) {
//            paths.filter(Files::isRegularFile) // Filtra apenas arquivos regulares (imagens)
//                    .map(path -> path.getFileName().toString()) // Obtém o nome do arquivo
//                    .filter(imageName -> !imageNames.contains(imageName)) // Evita duplicatas
//                    .forEach(imageNames::add); // Adiciona o nome à lista
//        }
//
//        // Atualizar a lista de nomes de imagens no Watch
//        watch.setImageUrls(imageNames);
//
//        // Persistir as alterações no banco de dados
//        watchRepository.persist(watch);
//    }




}
