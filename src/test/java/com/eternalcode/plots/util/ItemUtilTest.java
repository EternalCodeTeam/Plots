package com.eternalcode.plots.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

class ItemUtilTest {

    @Test
    @DisplayName("Compute item amount correctly")
    void computeItemAmountCorrectly() {
        ItemStack sampleStack = Mockito.mock(ItemStack.class);
        ItemStack searchedStack = Mockito.mock(ItemStack.class);

        Mockito.when(searchedStack.isSimilar(sampleStack)).thenReturn(true);
        Mockito.when(searchedStack.getAmount()).thenReturn(5);

        Inventory inventory = Mockito.mock(Inventory.class);
        Mockito.when(inventory.getSize()).thenReturn(10);
        Mockito.when(inventory.getItem(anyInt())).thenReturn(searchedStack);

        int amount = ItemUtil.getAmount(inventory, sampleStack);
        assertEquals(50, amount, "Amount should be correct");
    }
}