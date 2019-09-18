package ar.edu.uade.integracion.shop.entity;

public enum Warranty {

    NONE("none"),
    SIXMONTHS("six-months"),
    ONEYEAR("one-year"),
    TWOYEARS("two-year");

    private String name;

    Warranty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Warranty fromName(String text) {
        for (Warranty warranty : Warranty.values()) {
            if (warranty.getName().equalsIgnoreCase(text)) {
                return warranty;
            }
        }
        return Warranty.NONE;
    }
}
