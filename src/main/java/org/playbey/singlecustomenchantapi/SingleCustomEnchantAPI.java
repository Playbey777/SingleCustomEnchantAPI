package org.playbey.singlecustomenchantapi;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class SingleCustomEnchantAPI extends JavaPlugin {

    private static SingleCustomEnchantAPI instance;
    private Enchantment glowEnchant;

    @Override
    public void onLoad() {
        instance = this;
        registerGlowEnchant();
    }

    @Override
    public void onEnable() {
        getLogger().info("SingleCustomEnchantAPI Enabled!");
    }

    private void registerGlowEnchant() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            NamespacedKey key = new NamespacedKey(this, "glow");
            GlowEnchant customGlow = new GlowEnchant(key);

            Enchantment.registerEnchantment(customGlow);
            glowEnchant = customGlow;
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
            Enchantment existing = Enchantment.getByKey(new NamespacedKey(this, "glow"));
            if (existing != null) {
                glowEnchant = existing;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SingleCustomEnchantAPI getInstance() {
        return instance;
    }

    public Enchantment getGlowEnchant() {
        return glowEnchant;
    }
}