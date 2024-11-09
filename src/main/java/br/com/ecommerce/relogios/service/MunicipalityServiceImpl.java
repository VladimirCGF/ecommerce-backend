package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.MunicipalityDTO;
import br.com.ecommerce.relogios.dto.MunicipalityResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.Municipality;
import br.com.ecommerce.relogios.model.State;
import br.com.ecommerce.relogios.repository.MunicipalityRepository;
import br.com.ecommerce.relogios.repository.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class MunicipalityServiceImpl implements MunicipalityService {

    @Inject
    MunicipalityRepository municipalityRepository;

    @Inject
    StateRepository stateRepository;

    @Transactional
    @Override
    public List<MunicipalityResponseDTO> findAll() {
        return municipalityRepository
                .listAll()
                .stream()
                .map(MunicipalityResponseDTO::valueOf).toList();
    }

    @Override
    public MunicipalityResponseDTO findMunicipalityById(Long id) {
        if (municipalityRepository.findById(id) == null) {
            throw new NotFoundException("id municipality not found");
        }
        return MunicipalityResponseDTO.valueOf(municipalityRepository.findById(id));
    }

    @Transactional
    @Override
    public MunicipalityResponseDTO create(MunicipalityDTO municipalityDTO) {
        validMunicipalityDTO(municipalityDTO);
        Municipality municipality = new Municipality();
        State state = stateRepository.findById(municipalityDTO.idState());
        municipality.setState(state);
        municipality.setName(municipalityDTO.name());
        municipalityRepository.persist(municipality);
        return MunicipalityResponseDTO.valueOf(municipality);
    }

    @Transactional
    @Override
    public void update(Long id, MunicipalityDTO municipalityDTO) {
        Municipality municipality = municipalityRepository.findById(id);
        if (municipality == null) {
            throw new NotFoundException("id municipality not found");
        }
        State state = stateRepository.findById(municipalityDTO.idState());
        if (state == null) {
            throw new NotFoundException("id state not found");
        }
        Municipality municipalityDB = municipalityRepository.findByName(municipalityDTO.name());
        if (!municipalityDB.getId().equals(municipality.getId())) {
            throw new ValidationException("id de município deve ser iguais");
        }
        municipality.setState(state);
        municipality.setName(municipalityDTO.name());
        municipalityRepository.persist(municipality);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (municipalityRepository.findById(id) == null) {
            throw new NotFoundException("id municipality not found");
        }
        municipalityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<MunicipalityResponseDTO> getMunicipalitiesByState(Long idState) {
        return municipalityRepository.findByStateId(idState)
                .stream()
                .map(MunicipalityResponseDTO::valueOf).toList();
    }

    @Override
    public boolean checkNameUnique(Long id, String name, Long idState) {
        String normalizedName = name.trim();
        Municipality existingMunicipality = municipalityRepository.findByNameAndStateId(normalizedName, idState);

        if (existingMunicipality == null) {
            return true;
        }
        if (existingMunicipality.getId().equals(id)) {
            return true;
        }

        return existingMunicipality == null;
    }


    private void validMunicipalityDTO(MunicipalityDTO municipalityDTO) {
        if (municipalityDTO.name() == null || municipalityDTO.name().isEmpty()) {
            throw new ValidationException("O nome é obrigatório.");
        }

        if (municipalityRepository.find("name", municipalityDTO.name().toUpperCase()).count() > 0) {
            throw new ValidationException("O nome do município já existe.");
        }

        if (stateRepository.findById(municipalityDTO.idState()) == null) {
            throw new NotFoundException("ID estado não existe");
        }

    }
}
