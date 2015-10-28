package br.neitan96.swordlevelv3.conditions;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by neitan96 on 28/10/15.
 */
public interface Conditions extends ConfigLoader{

    String[] getWorldsWhite();

    boolean validWhiteWorld(String world);

    String[] getWorldsBlack();

    boolean validBlackWorld(String world);

    Material[] getMaterials();

    boolean hasMaterial(Material material);

    String[] getNames();

    boolean hasName(String name);

    String[] getNamesContains();

    boolean hasNameContains(String name);

    String[] getLoreLines();

    boolean hasLoreLine(List<String> lore);

    String[] getLoreContains();

    boolean hasLoreContains(List<String> lore);

    boolean conditionValid(Player player, ItemStack item);

}
