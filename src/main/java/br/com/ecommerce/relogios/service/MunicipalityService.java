package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.MunicipalityDTO;
import br.com.ecommerce.relogios.dto.MunicipalityResponseDTO;

import java.util.List;

public interface MunicipalityService {

    public List<MunicipalityResponseDTO> findAll();

    public MunicipalityResponseDTO findMunicipalityById(Long id);

    public MunicipalityResponseDTO create(MunicipalityDTO municipalityDTO);

    public void update(Long id, MunicipalityDTO municipalityDTO);

    public void delete(Long id);

    List<MunicipalityResponseDTO> getMunicipalitiesByState(Long stateId);

    boolean checkNameUnique(Long id, String name, Long idState);
}
