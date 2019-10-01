package ar.edu.uade.integracion.shop.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void fromName() {
        Enum type = Category.CAT1;
        int i = 1 ;

    }

    @Test
    public void getName() {
        try {
            Category type = Category.fromName("Categoria1");
            int i = 1 ;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getId() {
    }
}