package com.pphi.iron.dragon.component;

import com.iron.dragon.exceptions.UnsupportedEnumException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class defines the number of possible load types and the number of loads available as outlined in the
 * Iron Dragon Rules.pdf on page 7.
 */
public enum Load {
    ALE("Ale", 4),
    ARMOR("Armor", 4),
    ARMS("Arms",4),
    CATTLE("Cattle", 4),
    CLOTH("Cloth", 4),
    DRAGONS("Dragons", 3),
    FISH("Fish", 3),
    FRUIT("Fruit", 3),
    FURS("Furs", 4),
    GEMS("Gems", 3),
    GOLD("Gold", 4),
    HOPS("Hops", 4),
    IRON("Iron", 4),
    IVORY("Ivory", 4),
    JEWELRY("Jewelry", 4),
    LUMBER("Lumber", 3),
    PILGRIMS("Pilgrims" , 3),
    PIPEWEED("Pipeweed" , 4),
    POTIONS("Potions" , 4),
    SHEEP("Sheep" , 4),
    SPELLS("Spells" , 3),
    SPICE("Spice" , 4),
    STEEL("Steel" , 4),
    WANDS("Wands" , 3),
    WHEAT("Wheat" , 3),
    WINE("Wine" , 4);

    private final String name;
    private final int quantity;

    Load(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Load getLoad(String name) {
        checkNotNull(name, "The load name cannot be null");
        for (Load load : Load.values()) {
            if (name.trim().equalsIgnoreCase(load.getName())) {
                return load;
            }
        }
        throw new UnsupportedEnumException("Load " + name + " is not supported");
    }
}
