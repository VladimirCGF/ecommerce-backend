package br.com.ecommerce.relogios.service;

import br.com.ecommerce.relogios.dto.*;
import br.com.ecommerce.relogios.exceptions.EmailAlreadyInUseException;
import br.com.ecommerce.relogios.exceptions.ValidationException;
import br.com.ecommerce.relogios.model.*;
import br.com.ecommerce.relogios.repository.*;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientServiceImpl implements ClientService {

    @Inject
    ClientRepository clientRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    AddressRepository addressRepository;

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    CouponRepository couponRepository;

    @Inject
    WatchRepository watchRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    StateRepository stateRepository;

    @Inject
    MunicipalityRepository municipalityRepository;

    @Inject
    HashService hashService;

    @Inject
    SecurityIdentity securityIdentity;

    @Transactional
    @Override
    public List<ClientResponseDTO> findAll() {
        return clientRepository
                .listAll()
                .stream()
                .map(ClientResponseDTO::valueOf).toList();
    }

    @Override
    public ClientResponseDTO findClientById(Long id) {
        if (clientRepository.findById(id) == null) {
            throw new NotFoundException("id user not found");
        }
        return ClientResponseDTO.valueOf(clientRepository.findById(id));
    }

    @Transactional
    @Override
    public ClientResponseDTO create(ClientDTO clientDTO) {
        validUserDTO(clientDTO);

        Client exist = clientRepository.findByCpf(clientDTO.cpf());
        if (exist != null) {
            throw new ValidationException("cpf already exists");
        }

        User user = new User();
        user.setEmail(clientDTO.email());
        user.setPassword(hashService.getHashPassword(clientDTO.password()));
        user.setRole(Role.CLIENT);
        userRepository.persist(user);

        Client client = new Client();
        client.setFirstName(clientDTO.firstName());
        client.setLastName(clientDTO.lastName());
        client.setCpf(clientDTO.cpf());
        client.setUser(user);
        clientRepository.persist(client);

        return ClientResponseDTO.valueOf(client);
    }

    @Transactional
    @Override
    public ClientResponseDTO createClient(ClientDTO clientDTO) {
        validUserDTO(clientDTO);
        User user = new User();
        user.setEmail(clientDTO.email());
        user.setPassword(hashService.getHashPassword(clientDTO.password()));
        user.setRole(Role.CLIENT);
        userRepository.persist(user);

        Client client = new Client();
        client.setUser(user);
        clientRepository.persist(client);
        return ClientResponseDTO.valueOf(client);
    }

    @Transactional
    @Override
    public void update(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        Client existingCPF = clientRepository.findByCpf(clientDTO.cpf());
        if (!existingCPF.getId().equals(client.getId())) {
            throw new ValidationException("id de cpf deve ser iguais");
        }
        client.setFirstName(clientDTO.firstName());
        client.setLastName(clientDTO.lastName());
        client.setCpf(clientDTO.cpf());
        client.getUser().setEmail(clientDTO.email());
        client.getUser().setPassword(hashService.getHashPassword(clientDTO.password()));
        clientRepository.persist(client);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (clientRepository.findById(id) == null) {
            throw new NotFoundException("id client not found");
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean checkEmailUnique(Long id, String email) {
        String normalizedCode = email.trim().toUpperCase();
        Client existing = clientRepository.findByEmail(normalizedCode);
        if (existing.getId().equals(id)) {
            return true;
        }
        return existing == null;
    }

    @Transactional
    @Override
    public Client getLoggedClient() {
        String email = securityIdentity.getPrincipal().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Client client = clientRepository.findByEmail(user.getEmail());
        if (client == null) {
            throw new NotFoundException("Client not found");
        }
        return client;
    }

    @Transactional
    @Override
    public void addAddress(AddressDTO addressDTO) {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Address address = new Address();
        Client client = clientRepository.findByEmail(user.getEmail());
        State state = stateRepository.findById(addressDTO.idState());
        if (state == null) {
            throw new NotFoundException("id state not found");
        }
        Municipality municipality = municipalityRepository.findById(addressDTO.idMunicipality());
        if (municipality == null) {
            throw new NotFoundException("id municipality not found");
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
    public void removeAddress(Long id) {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());
        Address address = addressRepository.findById(id);
        if (address == null) {
            throw new NotFoundException("id address not found");
        }
        client.getAddresses().remove(address);
        address.setClient(null);
        addressRepository.persist(address);
        clientRepository.persist(client);
        clientRepository.flush();
        clientRepository.getEntityManager().clear();
    }

    @Transactional
    @Override
    public void addItem(AddItemOrderDTO addItemOrderDTO) {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());

        OrderItem orderItem = new OrderItem();
        Orders orders = ordersRepository.findPendingOrderByClientId(client.getId());
        if (orders == null) {
            orders = new Orders();
        }
        Watch watch = watchRepository.findById(addItemOrderDTO.idWatch());
        if (watch == null) {
            throw new NotFoundException("id watch not found");
        }
        orderItem.setOrders(orders);
        orders.setOrderDate(LocalDateTime.now());
        orders.setClient(client);
        orderItem.setWatch(watch);
        orderItem.setQuantity(addItemOrderDTO.quantity());
        orders.setStatus(OrdersStatus.PENDING);
        orderItem.setPrice(watch.getPrice() * orderItem.getQuantity());
        double discount = orders.getCoupon() != null ? orders.getCoupon().getDiscountPercentage() / 100 : 0.0;
        double totalItemPrice = orderItem.getPrice() - (orderItem.getPrice() * discount);
        orders.setTotalPrice(orders.getTotalPrice() + totalItemPrice);
        ordersRepository.persist(orders);
        orderItemRepository.persist(orderItem);
    }

    @Transactional
    @Override
    public void removerItem(Long id) {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());

        Orders orders = ordersRepository.findPendingOrderByClientId(client.getId());
        if (orders == null) {
            orders = new Orders();
        }
        Optional<OrderItem> itemToRemove = orders.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(id))
                .findFirst();

        if (itemToRemove.isPresent()) {
            OrderItem item = itemToRemove.get();
            orders.getOrderItems().remove(item);
            double newTotalPrice = orders.getOrderItems().stream()
                    .mapToDouble(OrderItem::getPrice)
                    .sum();
            orders.setTotalPrice(newTotalPrice);
            orderItemRepository.delete(item);
            ordersRepository.persist(orders);
        } else {
            throw new NotFoundException("Item não encontrado no pedido.");
        }
    }

    @Transactional
    @Override
    public OrdersResponseDTO checkout(OrdersDTO ordersDTO) {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());

        Orders orders = ordersRepository.findPendingOrderByClientId(client.getId());
        Coupon coupon = couponRepository.findByCode(ordersDTO.coupon());
        if (coupon == null) {
            throw new NotFoundException("coupon not found");
        }

        Address address = addressRepository.findById(ordersDTO.idAddress());
        if (address == null) {
            throw new NotFoundException("address not found");
        }
        orders.setCoupon(coupon);
        double totalOrderPrice = orders.getOrderItems().stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
        double discount = coupon.getDiscountPercentage() / 100.0;
        double totalWithDiscount = totalOrderPrice - (totalOrderPrice * discount);
        orders.setTotalPrice(totalWithDiscount);
        orders.setAddress(address);
        orders.setStatus(OrdersStatus.WAITING_FOR_PAYMENT);
        orders.setPaymentDeadline(LocalDateTime.now().plusMinutes(1));
        ordersRepository.persist(orders);
        return OrdersResponseDTO.valueOf(orders);
    }

    @Transactional
    @Override
    public void payment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        Orders orders = ordersRepository.findById(paymentDTO.idOrders());
        if (orders == null) {
            throw new NotFoundException("orders not found");
        }
        if (orders.getStatus() == OrdersStatus.CANCELLED) {
            throw new ValidationException("orders cancelled");
        }
        payment.setOrders(orders);
        payment.setPrice(orders.getTotalPrice());
        payment.setPaymentDateTime(LocalDateTime.now());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentDTO.paymentMethod()));
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        orders.setStatus(OrdersStatus.PAID);
        paymentRepository.persist(payment);
    }

    @Transactional
    @Override
    public List<PaymentResponseDTO> findMyPayments() {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());
        List<Payment> payments = paymentRepository.findPaymentsByClientId(client.getId());
        return payments.stream().map(PaymentResponseDTO::valueOf).toList();
    }

    @Transactional
    @Override
    public List<AddressResponseDTO> findMyListAddress() {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());
        return client.getAddresses().stream().map(AddressResponseDTO::valueOf).toList();
    }

    @Transactional
    @Override
    public List<OrdersResponseDTO> findMyListOrders() {
        User user = userRepository.findByEmail(getLoggedClient().getUser().getEmail());
        Client client = clientRepository.findByEmail(user.getEmail());
        List<Orders> orders = ordersRepository.findListByIdCliente(client.getId());
        return orders.stream().map(OrdersResponseDTO::valueOf).toList();
    }

    @Transactional
    @Override
    public UserResponseDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new ValidationException("O login: email ou senha incorreta");
        }
        return UserResponseDTO.valueOf(user);
    }



    private void validUserDTO(ClientDTO clientDTO) {
        if (userRepository.find("email", clientDTO.email()).count() > 0) {
            throw new EmailAlreadyInUseException("O email já está em uso.");
        }
        if (clientDTO.password() == null || clientDTO.password().isEmpty()) {
            throw new ValidationException("A senha é obrigatória.");
        }
        if (clientDTO.password().length() < 3) {
            throw new ValidationException("A senha deve ter no mínimo 8 caracteres.");
        }
    }

    @Transactional
    @Override
    @Scheduled(cron = "*/10 * * * * ?")
    public void cancelExpiredOrders() {
        List<Orders> expiredOrders = ordersRepository.findExpiredOrders(LocalDateTime.now());
        for (Orders order : expiredOrders) {
            order.setStatus(OrdersStatus.CANCELLED);
            ordersRepository.persist(order);
        }
    }

}
