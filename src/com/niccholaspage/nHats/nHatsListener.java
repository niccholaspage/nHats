package com.niccholaspage.nHats;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class nHatsListener implements Listener {
    private final nHats plugin;

    public nHatsListener(nHats plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<ItemStack> drops = new ArrayList<ItemStack>(event.getDrops());

        for (ItemStack drop : drops) {
            if (plugin.getType(drop) != null) {
                event.getDrops().remove(drop);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        ItemStack stack = event.getCurrentItem();

        if (stack == null || stack.getType() == Material.AIR) {
            return;
        }

        HatType type = plugin.getType(stack);

        if (type != null) {
            event.setCancelled(true);
        }

        if (!inventory.getTitle().equals(nHats.INVENTORY_TITLE)) {
            return;
        }

        event.setCancelled(true);

        List<HumanEntity> entities = event.getViewers();

        for (HumanEntity entity : entities) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                ItemStack helmet = player.getInventory().getHelmet();

                if (stack.getType() == plugin.getUnequipButton().getType()) {
                    if (helmet == null || plugin.getType(helmet) == null) {
                        player.sendMessage(ChatColor.RED + "You can't unequip air or a piece of armor!");

                        return;
                    }

                    player.getInventory().setHelmet(null);

                    player.sendMessage(ChatColor.GREEN + "Hat unequipped!");

                    return;
                }

                String typeName = type.name().toLowerCase();

                if (!player.hasPermission("nhats." + typeName)) {
                    player.sendMessage(ChatColor.RED + "Buy the " + typeName + " hat package from the InfiniteCraft store (http://goo.gl/GtsdtI) to access this hat!");

                    return;
                }

                if (helmet != null && plugin.getType(helmet) == null) {
                    player.sendMessage(ChatColor.RED + "Unequip your armor to put on a hat.");

                    return;
                }

                player.getInventory().setHelmet(stack);

                player.sendMessage(ChatColor.GREEN + "You've equipped a " + stack.getItemMeta().getDisplayName() + "!");
            }
        }
    }
}
