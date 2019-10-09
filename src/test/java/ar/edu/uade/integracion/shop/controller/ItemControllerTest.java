package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.*;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ar.edu.uade.integracion.shop.mock.ItemMock.createItemDtoFromItem;
import static ar.edu.uade.integracion.shop.mock.ItemMock.createMockItem;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void whenPostingItemThenItemShouldBePersisted() throws Exception {
        Item mock = createMockItem();
        ItemDto dto = createItemDtoFromItem(mock);
        dto.setId(null);
        when(itemRepository.save(any())).thenReturn(mock);
        mockMvc.perform(post("/items").content(mapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(mapper.writeValueAsString(createItemDtoFromItem(mock))));
        verify(itemRepository).save(any());
    }

    @Test
    public void whenSearchingByCategoryShouldReturnList() throws Exception {
        Item testItem = createMockItem();
        ItemDto dto = createItemDtoFromItem(testItem);

        when(itemRepository.findByCategory(eq(Category.CELULARES), any())).thenReturn(Lists.newArrayList(testItem));

        mockMvc.perform(get("/items/category/"+Category.CELULARES)).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(Lists.newArrayList(dto))));
    }

    @Test
    public void gettingItemByIdShouldReturnItemInfo() throws Exception {
        Item testItem = createMockItem();
        ItemDto dto = createItemDtoFromItem(testItem);

        when(itemRepository.findById(1)).thenReturn(Optional.of(testItem));

        mockMvc.perform(get("/items/1")).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @Test
    public void ifItemDoesntExistReturn404() throws Exception {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/items/2")).andExpect(status().isNotFound());
    }

    @Test
    public void itemsShouldReturnListOfItems() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(createMockItem());
        Page<Item> page = new PageImpl<Item>(items);
        when(itemRepository.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(
                content()
                        .json(mapper.writeValueAsString(Lists.newArrayList(createItemDtoFromItem(createMockItem())))));
    }

    @Test
    public void itemSearchShouldReturnListOfItems() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(createMockItem());
        when(itemRepository.findByNameContainsOrDescriptionContains(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(items);

        mockMvc.perform(get("/items?q=pepe")).andExpect(status().isOk()).andExpect(
                content()
                        .json(mapper.writeValueAsString(Lists.newArrayList(createItemDtoFromItem(createMockItem())))));
    }

}