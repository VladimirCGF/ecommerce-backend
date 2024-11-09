package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.AddressDTO;
import br.com.ecommerce.relogios.dto.AddressResponseDTO;

import java.util.List;

public interface AddressService {

    public List<AddressResponseDTO> findAll();

    public AddressResponseDTO findAddressById(Long id);

    public AddressResponseDTO create(AddressDTO addressDTO);

    public void update(Long id, AddressDTO addressDTO);

    public void delete(Long id);
}
