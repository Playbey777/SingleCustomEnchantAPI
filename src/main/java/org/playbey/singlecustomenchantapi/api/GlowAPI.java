package org.playbey.singlecustomenchantapi.api;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.playbey.singlecustomenchantapi.SingleCustomEnchantAPI;

public class GlowAPI {

    public static void addGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        int enchantCount = meta.getEnchants().size();
        if (meta.hasEnchant(SingleCustomEnchantAPI.getInstance().getGlowEnchant())) {
            enchantCount--;
        }

        if (enchantCount == 0) {
            meta.addEnchant(SingleCustomEnchantAPI.getInstance().getGlowEnchant(), 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(SingleCustomEnchantAPI.getInstance().getGlowEnchant());
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
    }

    public static void removeGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        meta.removeEnchant(SingleCustomEnchantAPI.getInstance().getGlowEnchant());
        meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
    }

    public static boolean hasGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().hasEnchant(SingleCustomEnchantAPI.getInstance().getGlowEnchant());
    }
}