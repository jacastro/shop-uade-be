package ar.edu.uade.integracion.shop.entity;

public enum  Category {

    // TODO > Agregar las categorias.
    CAT1(1, "Categoria1"),
    CAT2(2, "Categoria2"),
    CAT3(3, "Categoria3");

    private String name;
    private int id;

    Category(int id, String name) {
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

    public static Category fromId(int id) throws Exception {
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

    public int getId() { return id; }
}
