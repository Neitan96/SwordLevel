package br.neitan96.swordlevelv3.events;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.manager.GroupManager;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 02/Nov/2015 20:02
 * Created by Neitan96 on 02/11/15.
 */
public class Bonuses implements Listener{

    protected final GroupManager manager;

    public Bonuses(GroupManager manager){
        this.manager = manager;
        Bukkit.getPluginManager().registerEvents(this, SwordLevel.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGH)
    protected void onHit(EntityDamageByEntityEvent event){

        if(!(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getDamager();
        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();
        Entity entity = event.getEntity();

        if(itemInHand == null || itemInHand.getType() == Material.AIR)
            return;

        Group group = manager.getGroupConditions(player, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(player);
        Bonus bonus = group.getBonus(permission);
        StorageLevel storageLevel = group.getStorageLevel(uuidPlayer, itemInHand);

        bonus.applyBonus(event, storageLevel.getLevel(), player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    protected void onDeath(EntityDeathEvent event){

        if(event.getEntity().getKiller() == null)
            return;

        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();
        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();

        if(itemInHand == null || itemInHand.getType() == Material.AIR)
            return;

        Group group = manager.getGroupConditions(player, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(player);
        Bonus bonus = group.getBonus(permission);
        StorageLevel storageLevel = group.getStorageLevel(uuidPlayer, itemInHand);

        bonus.applyBonus(event, storageLevel.getLevel(), player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    protected void onBreak(BlockBreakEvent event){

        if(event.isCancelled() || event.getPlayer() == null)
            return;

        Player player = event.getPlayer();
        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();

        if(itemInHand == null || itemInHand.getType() == Material.AIR)
            return;

        Group group = manager.getGroupConditions(player, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(player);
        Bonus bonus = group.getBonus(permission);
        StorageLevel storageLevel = group.getStorageLevel(uuidPlayer, itemInHand);

        bonus.applyBonus(event, storageLevel.getLevel(), player);
    }
}
