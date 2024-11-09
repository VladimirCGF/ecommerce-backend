package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StateDTO;
import br.com.ecommerce.relogios.dto.StateResponseDTO;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.State;
import br.com.ecommerce.relogios.repository.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class StateServiceImpl implements StateService {

    @Inject
    StateRepository stateRepository;

    @Transactional
    @Override
    public List<StateResponseDTO> findAll() {
        return stateRepository
                .listAll()
                .stream()
                .map(StateResponseDTO::valueOf).toList();
    }

    @Override
    public StateResponseDTO findStateById(Long id) {
        if (stateRepository.findById(id) == null) {
            throw new NotFoundException("id state not found");
        }
        return StateResponseDTO.valueOf(stateRepository.findById(id));
    }

    @Transactional
    @Override
    public StateResponseDTO create(StateDTO stateDTO) {
        validStateDTO(stateDTO);
        State state = new State();
        state.setAbbreviations(stateDTO.abbreviations());
        state.setName(stateDTO.name());
        stateRepository.persist(state);
        return StateResponseDTO.valueOf(state);
    }

    @Transactional
    @Override
    public void update(Long id, StateDTO stateDTO) {
        State state = stateRepository.findById(id);
        if (state == null) {
            throw new NotFoundException("id state not found");
        }
        state.setAbbreviations(stateDTO.abbreviations());
        state.setName(stateDTO.name());
        stateRepository.persist(state);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (stateRepository.findById(id) == null) {
            throw new NotFoundException("id state not found");
        }
        stateRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean checkAbbreviationsUnique(Long id, String abbreviations) {
        State existingAbbreviations = stateRepository.findByAbbreviations(abbreviations.trim().toUpperCase());
        if (existingAbbreviations.getId().equals(id)) {
            return true;
        }
        return existingAbbreviations == null;
    }

    @Transactional
    @Override
    public boolean checkNameUnique(Long id, String name) {
        State existingName = stateRepository.findByName(name.trim().toUpperCase());
        if (existingName.getId().equals(id)) {
            return true;
        }
        return existingName == null;
    }

    private void validStateDTO(StateDTO stateDTO) {

        if (stateDTO.name() == null || stateDTO.name().isEmpty()) {
            throw new ValidationException("O nome é obrigatório.");
        }

        if (stateDTO.abbreviations() == null || stateDTO.abbreviations().isEmpty()) {
            throw new ValidationException("A sigla é obrigatório.");
        }

        if (stateRepository.find("UPPER(name)", stateDTO.name().toUpperCase()).count() > 0) {
            throw new ValidationException("O nome do estado já existe.");
        }

        if (stateRepository.find("UPPER(abbreviations)", stateDTO.abbreviations().toUpperCase()).count() > 0) {
            throw new ValidationException("A sigla do estado já existe.");
        }

    }
}
