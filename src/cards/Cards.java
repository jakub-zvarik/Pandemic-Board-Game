package cards;

abstract class Cards {

    private final String NAME;

    public Cards(String name) {
        this.NAME = name;
    }

    public String getNAME() {
        return this.NAME;
    }
}
