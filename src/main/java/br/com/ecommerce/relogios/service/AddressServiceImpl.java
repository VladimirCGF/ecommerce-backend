package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.AddressDTO;
import br.com.ecommerce.relogios.dto.AddressResponseDTO;
import br.com.ecommerce.relogios.model.Address;
import br.com.ecommerce.relogios.model.Client;
import br.com.ecommerce.relogios.model.Municipality;
import br.com.ecommerce.relogios.model.State;
import br.com.ecommerce.relogios.repository.AddressRepository;
import br.com.ecommerce.relogios.repository.ClientRepository;
import br.com.ecommerce.relogios.repository.MunicipalityRepository;
import br.com.ecommerce.relogios.repository.StateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AddressServiceImpl implements AddressService {

    @Inject
    AddressRepository addressRepository;

    @Inject
    StateRepository stateRepository;

    @Inject
    MunicipalityRepository municipalityRepository;


    @Inject
    ClientRepository clientRepository;

    @Transactional
    @Override
    public List<AddressResponseDTO> findAll() {
        return addressRepository
                .listAll()
                .stream()
                .map(AddressResponseDTO::valueOf).toList();
    }

    @Override
    public AddressResponseDTO findAddressById(Long id) {
        if (addressRepository.findById(id) == null) {
            throw new NotFoundException("id address not found");
        }
        return AddressResponseDTO.valueOf(addressRepository.findById(id));
    }

    @Transactional
    @Override
    public AddressResponseDTO create(AddressDTO addressDTO) {
        Address address = new Address();
        State state = stateRepository.findById(addressDTO.idState());
        if (state == null) {
            throw new NotFoundException("id state not found");
        }
        Municipality municipality = municipalityRepository.findById(addressDTO.idMunicipality());
        if (municipality == null) {
            throw new NotFoundException("id municipality not found");
        }
        Client client = clientRepository.findById(addressDTO.idClient());
        if (client == null) {
            throw new NotFoundException("client not found");
        }
        address.setAddress(addressDTO.address());
        address.setState(state);
        address.setMunicipality(municipality);
        address.setCep(addressDTO.cep());
        address.setClient(client);
        addressRepository.persist(address);
        return AddressResponseDTO.valueOf(address);
    }

    @Transactional
    @Override
    public void update(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id);
        if (address == null) {
            throw new NotFoundException("id address not found");
        }
        State state = stateRepository.findById(addressDTO.idState());
        if (state == null) {
            throw new NotFoundException("id state not found");
        }
        Municipality municipality = municipalityRepository.findById(addressDTO.idMunicipality());
        if (municipality == null) {
            throw new NotFoundException("id municipality not found");
        }
        Client client = clientRepository.findById(addressDTO.idClient());
        if (client == null) {
            throw new NotFoundException("client not found");
        }
        address.setAddress(addressDTO.address());
        address.setState(state);
        address.setMunicipality(municipality);
        address.setCep(addressDTO.cep());
        address.setClient(client);
        addressRepository.persist(address);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (addressRepository.findById(id) == null) {
            throw new NotFoundException("id address not found");
        }
        addressRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<AddressResponseDTO> findAddressByIdClient(Long id) {
        List<Address> list = addressRepository.findByIdClient(id);
        return list.stream().map(AddressResponseDTO::valueOf).toList();
    }
}
