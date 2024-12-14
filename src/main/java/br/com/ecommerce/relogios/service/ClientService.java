package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.*;
import br.com.ecommerce.relogios.model.Client;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ClientService {

    public List<ClientResponseDTO> findAll();

    public ClientResponseDTO findClientById(Long id);

    public ClientResponseDTO create(ClientDTO clientDTO);

    ClientResponseDTO createClient(ClientDTO clientDTO);

    public void update(Long id, ClientDTO clientDTO);

    public void delete(Long id);

    public Client getLoggedClient();

    public void addAddress(AddressDTO addressDTO);

    public void removeAddress(Long id);

    public void addItem(AddItemOrderDTO addItemOrderDTO);

    void removerItem(Long id);

    public OrdersResponseDTO checkout(OrdersDTO ordersDTO);

    public void payment(PaymentDTO paymentDTO);

    public List<PaymentResponseDTO> findMyPayments();

    public List<AddressResponseDTO> findMyListAddress();

    List<OrderItemResponseDTO> findMyOrderItems(Long idOrder);

    public List<OrdersResponseDTO> findMyListOrders();

    public UserResponseDTO login(String email, String password);

    boolean checkEmailUnique(Long id, String email);

    ClientResponseDTO findByEmail(String email);


//    public void cancelExpiredOrders();

}
