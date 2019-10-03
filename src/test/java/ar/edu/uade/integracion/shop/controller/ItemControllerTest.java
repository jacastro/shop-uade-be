package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.*;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void gettingItemByIdShouldReturnItemInfo() throws Exception {
        Item testItem = new Item();
        testItem.setId(1);
        testItem.setCategory(Category.CELULARES);
        testItem.setDescription("desc");
        testItem.setName("test");
        testItem.setPrice(10.0);
        testItem.setWarranty(Warranty.NONE);
        testItem.setWeight(10.0);
        User userTest = new User();
        userTest.setId("1");
        userTest.setName("testu");
        testItem.setSeller(userTest);

        ItemDto dto = new ItemDto();
        dto.setCategory(Category.CELULARES);
        dto.setDescription("desc");
        dto.setId(1);
        dto.setName("test");
        dto.setPrice(10.0);
        dto.setWarranty(Warranty.NONE);
        dto.setSeller(new UserDto("1", "testu"));
        dto.setWeight(10.0);

        when(itemRepository.findById(1)).thenReturn(Optional.of(testItem));

        mockMvc.perform(get("/items/1")).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dto)));
    }

    @Test
    public void ifItemDoesntExistReturn404() throws Exception {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/items/2")).andExpect(status().isNotFound());
    }

}
