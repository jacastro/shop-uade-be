package ar.edu.uade.integracion.shop.mock;

import ar.edu.uade.integracion.shop.entity.Category;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.ItemDto;
import ar.edu.uade.integracion.shop.entity.Warranty;

public class ItemMock {
    private ItemMock(){}

    public static Item createMockItem(){
        Item testItem = new Item();
        testItem.setId(1);
        testItem.setCategory(Category.CELULARES);
        testItem.setDescription("desc");
        testItem.setName("test");
        testItem.setPrice(10.0);
        testItem.setWarranty(Warranty.NONE);
        testItem.setWeight(10.0);

        testItem.setSeller(UserMock.createUser());

        return testItem;
    }

    public static ItemDto createItemDtoFromItem(Item item){
        ItemDto dto = new ItemDto();
        dto.setCategory(item.getCategory());
        dto.setDescription(item.getDescription());
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setWarranty(item.getWarranty());
        dto.setSeller(UserMock.createUserDtoFromUser(item.getSeller()));
        dto.setWeight(item.getWeight());

        return dto;
    }
}
