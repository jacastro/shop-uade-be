package ar.edu.uade.integracion.shop.entity;

public enum  Category {

    // TODO > Agregar las categorias.
    ELECTRODOMESTICOS(1, "Electrodomesticos"),
    MUEBLES(2, "Muebles"),
    MODA(3, "Moda"),
    CELULARES(4, "Celulares"),
    CAT1(5, "cat1"),
    CAT2(5, "cat2"),
    CAT3(5, "cat3");

    private String name;
    private Integer id;

    Category(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public static Category fromName(String name) throws Exception {
        for (Category category : Category.values()) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new Exception("Not found");
    }

    public static Category fromId(Integer id) throws Exception {
        for (Category category : Category.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new Exception("Not found");
    }

    public String getName() {
        return name;
    }

    public Integer getId() { return id; }
}
