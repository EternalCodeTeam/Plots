package com.eternalcode.plots.flag;

public enum FlagType {

    // Allows to place blocks
    BLOCK_PLACE,

    // Allows to break blocks
    BLOCK_BREAK,

    // Allows to attack other players on plot
    PVP,


    // Allows to atacck friendly mobs, eg. pigs, cows, etc.
    ATTACK_FRIENDLY_MOBS,

    // Allows to attack monsters, eg. zombies, skeletons, etc.
    MONSTERS,

    // Allows to destroy vehicles, eg. boats, minecarts, etc.
    VEHICLE_DESTROY,

    // Allows to click entities, eg. villagers, armor stands, etc.
    CLICK_ENTITY,

    // Allows to open chests, furnaces, etc.
    CHEST_ACCESS,

    // Allows to use blocks, eg. open doors, click buttons, etc.
    USE,

}
