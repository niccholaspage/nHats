package com.niccholaspage.nHats;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class nHats extends JavaPlugin {
    public final static String INVENTORY_TITLE = ChatColor.RESET + "" + ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "Hats";

    public void onEnable() {
        getCommand("hats").setExecutor(this);

        new nHatsListener(this);
    }

    public void showHatMenu(Player player) {
        Inventory inventory = getServer().createInventory(player, 27, INVENTORY_TITLE);

        inventory.addItem(getHat(Material.GRASS, HatType.NORMAL));
        inventory.addItem(getHat(Material.DIRT, HatType.NORMAL));
        inventory.addItem(getHat(Material.SAND, HatType.NORMAL));
        inventory.addItem(getHat(Material.WOOD, HatType.NORMAL));
        inventory.addItem(getHat(Material.LOG, HatType.NORMAL));
        inventory.addItem(getHat(Material.GLASS, HatType.NORMAL));
        inventory.addItem(getHat(Material.BRICK, HatType.NORMAL));
        inventory.addItem(getHat(Material.BOOKSHELF, HatType.NORMAL));
        inventory.addItem(getHat(Material.OBSIDIAN, HatType.NORMAL));

        inventory.addItem(getHat(Material.WORKBENCH, HatType.DELUXE));
        inventory.addItem(getHat(Material.FURNACE, HatType.DELUXE));
        inventory.addItem(getHat(Material.CHEST, HatType.DELUXE));
        inventory.addItem(getHat(Material.ENCHANTMENT_TABLE, HatType.DELUXE));
        inventory.addItem(getHat(Material.ANVIL, HatType.DELUXE));
        inventory.addItem(getHat(Material.WATER, HatType.DELUXE));
        inventory.addItem(getHat(Material.LAVA, HatType.DELUXE));
        inventory.addItem(getHat(Material.IRON_BLOCK, HatType.DELUXE));
        inventory.addItem(getHat(Material.DIAMOND_BLOCK, HatType.DELUXE));

        inventory.setItem(26, getUnequipButton());

        player.openInventory(inventory);
    }

    public ItemStack getHat(Material type, HatType hatType) {
        ItemStack stack = new ItemStack(type, 1);

        ItemMeta meta = stack.getItemMeta();

        String goodName = ChatColor.GREEN + WordUtils.capitalizeFully(type.name().replace("_", " ")) + " Hat";

        meta.setDisplayName(goodName);

        List<String> lore = new ArrayList<String>();

        lore.add(ChatColor.GRAY + getGoodName(hatType));

        meta.setLore(lore);

        stack.setItemMeta(meta);

        return stack;
    }

    public ItemStack getUnequipButton() {
        ItemStack stack = new ItemStack(Material.REDSTONE, 1);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Unequip Hat");

        stack.setItemMeta(meta);

        return stack;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("nhats.use")) {
            return true;
        }

        showHatMenu((Player) sender);

        return true;
    }

    public HatType getType(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            return null;
        }

        List<String> lore = meta.getLore();

        if (lore == null || lore.isEmpty()) {
            return null;
        }

        for (HatType type : HatType.values()) {
            if (lore.contains(ChatColor.GRAY + getGoodName(type))) {
                return type;
            }
        }

        return null;
    }

    public String getGoodName(HatType type) {
        return WordUtils.capitalizeFully(type.name().replace("_", " ")) + " Hat";
    }
}
