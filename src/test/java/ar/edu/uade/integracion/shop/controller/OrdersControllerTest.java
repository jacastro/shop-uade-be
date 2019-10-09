package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.OrderDto;
import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.mock.AddressMock;
import ar.edu.uade.integracion.shop.mock.UserMock;
import ar.edu.uade.integracion.shop.repository.AddressRepository;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static ar.edu.uade.integracion.shop.mock.AddressMock.createAddres;
import static ar.edu.uade.integracion.shop.mock.ItemMock.createItemDtoFromItem;
import static ar.edu.uade.integracion.shop.mock.ItemMock.createMockItem;
import static ar.edu.uade.integracion.shop.mock.UserMock.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrdersControllerTest {
    @MockBean
    private OrderRepository repository;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getOrderListWhenSearchingBySeller() throws Exception {
        User seller = UserMock.createUser();
        Order mock = createMockOrder();
        when(repository.findByItemSeller(seller)).thenReturn(Lists.newArrayList(mock));
        when(addressRepository.findById(any())).thenReturn(Optional.of(AddressMock.createAddres()));
        when(userRepository.findById(seller.getId())).thenReturn(Optional.of(seller));
        when(itemRepository.findById(any())).thenReturn(Optional.of(createMockItem()));

        mockMvc.perform(get("/orders/seller/1")).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Lists.newArrayList(createDto(mock)))));
    }

    @Test
    public void getOrderListWhenSearchingByBuyer() throws Exception {
        User buyer = UserMock.createUser();
        Order mock = createMockOrder();
        when(repository.findByBuyer(buyer)).thenReturn(Lists.newArrayList(mock));
        when(addressRepository.findById(any())).thenReturn(Optional.of(AddressMock.createAddres()));
        when(userRepository.findById(buyer.getId())).thenReturn(Optional.of(buyer));
        when(itemRepository.findById(any())).thenReturn(Optional.of(createMockItem()));

        mockMvc.perform(get("/orders/buyer/1")).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Lists.newArrayList(createDto(mock)))));
    }

    @Test
    public void whenPostingOrderItShouldBePersisted() throws Exception {
        Order mock = createMockOrder();
        OrderDto dto = createDto(mock);
        dto.setId(null);
        User user = createUser();
        when(addressRepository.findById(any())).thenReturn(Optional.of(AddressMock.createAddres()));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRepository.findById(any())).thenReturn(Optional.of(createMockItem()));
        when(repository.save(any())).thenReturn(mock);
        mockMvc.perform(post("/orders/").content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(mapper.writeValueAsString(createDto(mock))));
        verify(repository).save(any());
    }


    private Order createMockOrder() {
        Order order = new Order();
        order.setTotal(10.0);
        order.setQuantity(1);
        order.setItem(createMockItem());
        order.setDate(new Date());
        order.setBuyer(createUser());
        order.setAddress(createAddres());
        order.setId(1);
        return order;
    }

    private OrderDto createDto(Order model){
        OrderDto dto = new OrderDto();
        dto.setAddress(model.getAddress().getId());
        dto.setDate(model.getDate());
        dto.setId(model.getId());
        dto.setItemId(model.getItem().getId());
        dto.setQuantity(model.getQuantity());
        dto.setShippingId(model.getShippingId());
        dto.setTotal(model.getTotal());
        dto.setBuyerId(model.getBuyer().getId());
        return dto;
    }
}
