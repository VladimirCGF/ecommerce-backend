package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.StateDTO;
import br.com.ecommerce.relogios.dto.StateResponseDTO;

import java.util.List;

public interface StateService {

    public List<StateResponseDTO> findAll();

    public StateResponseDTO findStateById(Long id);

    public StateResponseDTO create(StateDTO stateDTO);

    public void update(Long id, StateDTO stateDTO);

    public void delete(Long id);

    boolean checkAbbreviationsUnique(Long id, String abbreviations);

    public boolean checkNameUnique(Long id, String name);
}
