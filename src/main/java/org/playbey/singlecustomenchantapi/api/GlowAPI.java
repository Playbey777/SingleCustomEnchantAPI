package org.playbey.singlecustomenchantapi.api;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.playbey.singlecustomenchantapi.SingleCustomEnchantAPI;

import java.lang.reflect.Method;

public class GlowAPI {

    private static boolean hasGlowOverrideMethod;
    private static Method setGlowOverrideMethod;
    private static Method getGlowOverrideMethod;

    static {
        try {
            setGlowOverrideMethod = ItemMeta.class.getMethod("setEnchantmentGlowOverride", Boolean.class);
            getGlowOverrideMethod = ItemMeta.class.getMethod("getEnchantmentGlowOverride");
            hasGlowOverrideMethod = true;
        } catch (NoSuchMethodException e) {
            hasGlowOverrideMethod = false;
        }
    }

    public static void addGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        if (hasGlowOverrideMethod) {
            try {
                int enchantCount = meta.getEnchants().size();
                if (enchantCount == 0) {
                    setGlowOverrideMethod.invoke(meta, true);
                } else {
                    setGlowOverrideMethod.invoke(meta, (Boolean) null);
                }
                item.setItemMeta(meta);
            } catch (Exception ignored) {}
            return;
        }

        Enchantment glow = SingleCustomEnchantAPI.getInstance().getGlowEnchant();
        if (glow == null) return;

        int enchantCount = meta.getEnchants().size();
        if (meta.hasEnchant(glow)) {
            enchantCount--;
        }

        if (enchantCount == 0) {
            meta.addEnchant(glow, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(glow);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
    }

    public static void removeGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();

        if (hasGlowOverrideMethod) {
            try {
                setGlowOverrideMethod.invoke(meta, (Boolean) null);
                item.setItemMeta(meta);
            } catch (Exception ignored) {}
            return;
        }

        Enchantment glow = SingleCustomEnchantAPI.getInstance().getGlowEnchant();
        if (glow == null) return;

        meta.removeEnchant(glow);
        meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
    }

    public static boolean hasGlow(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();

        if (hasGlowOverrideMethod) {
            try {
                Boolean override = (Boolean) getGlowOverrideMethod.invoke(meta);
                return override != null && override;
            } catch (Exception ignored) {}
            return false;
        }

        Enchantment glow = SingleCustomEnchantAPI.getInstance().getGlowEnchant();
        if (glow == null) return false;

        return meta.hasEnchant(glow);
    }
}