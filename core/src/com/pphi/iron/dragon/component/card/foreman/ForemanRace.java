package com.pphi.iron.dragon.component.card.foreman;

public enum ForemanRace {
    CATMAN ("Catman"),
    DWARF("Dwarf"),
    ELF_HALF_ELF("Elf/Half-Elf"),
    HUMAN("Human"),
    ORC("Orc"),
    TROLL("Troll"),
    WEE_FOLK("Wee Folk");

    private String race;

    ForemanRace(String race) {
        this.race = race;
    }

    public String toString() {
        return race;
    }
}
