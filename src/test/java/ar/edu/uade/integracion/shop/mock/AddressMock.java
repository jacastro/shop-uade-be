package ar.edu.uade.integracion.shop.mock;

import ar.edu.uade.integracion.shop.entity.Address;

public class AddressMock {
    private AddressMock(){}

    public static Address createAddres(){
        Address address = new Address();
        address.setCity("city");
        address.setId(1);
        address.setState("state");
        address.setStreet("street");
        address.setZipCode("zipcode");

        return address;
    }
}
