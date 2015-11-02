package br.neitan96.swordlevelv3.antitheft;

import br.neitan96.swordlevelv3.util.SwordUtil;
import br.neitan96.swordlevelv3.util.TimeMark;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 20:41
 * Created by Neitan96 on 30/10/15.
 */
public class AntiTheftDefault implements AntiTheft{

    protected String permissionAllow = null;

    protected int samePlayerCount = 0;
    protected int samePlayerTime = 0;

    protected int anyPlayerCount = 0;
    protected int anyPlayerTime = 0;

    protected int mobCount = 0;
    protected int mobTime = 0;

    protected int blockCount = 0;
    protected int blockTime = 0;

    protected TimeMark<String> playersKill = new TimeMark<>();
    protected TimeMark<String> mobsKill = new TimeMark<>();
    protected TimeMark<String> blockBreak = new TimeMark<>();

    public AntiTheftDefault(ConfigurationSection section) {
        loadFromConfig(section);
    }

    @Override
    public int getCountSamePlayer() {
        return samePlayerCount;
    }

    @Override
    public int getTimeSamePlayer() {
        return samePlayerTime;
    }

    @Override
    public int getCountAnyPlayers() {
        return anyPlayerCount;
    }

    @Override
    public int getTimeAnyPlayers() {
        return anyPlayerTime;
    }

    @Override
    public int getCountMob() {
        return mobCount;
    }

    @Override
    public int getTimeMob() {
        return mobTime;
    }

    @Override
    public int getCountBlock() {
        return blockCount;
    }

    @Override
    public int getTimeBlock() {
        return blockTime;
    }

    @Override
    public boolean validAction(Player player, Player entity){
        if(player.hasPermission(permissionAllow))
            return true;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);

        long nowSame = System.currentTimeMillis() - (getTimeSamePlayer() * 1000);
        long nowAny = System.currentTimeMillis() - (getTimeAnyPlayers() * 1000);

        boolean valid =
                playersKill.countMarks(uuidPlayer, nowSame) <= getCountSamePlayer() &&
                playersKill.countMarks(uuidPlayer, nowAny, SwordUtil.getUUIDPlayer(entity)) <= getCountAnyPlayers();

        if(!valid)
            playersKill.add(uuidPlayer, SwordUtil.getUUIDPlayer(entity));

        return valid;
    }

    @Override
    public boolean validAction(Player player, LivingEntity entity){
        if(entity instanceof Player)
            return validAction(player, ((Player) entity));

        if(player.hasPermission(permissionAllow))
            return true;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);

        long now = System.currentTimeMillis() - (getTimeMob() * 1000);

        boolean valid = mobsKill.countMarks(uuidPlayer, now) <= getCountMob();

        if(!valid)
            mobsKill.add(uuidPlayer);

        return valid;
    }

    @Override
    public boolean validAction(Player player, Block block){
        if(player.hasPermission(permissionAllow))
            return true;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);

        long now = System.currentTimeMillis() - (getTimeBlock() * 1000);

        boolean valid = blockBreak.countMarks(uuidPlayer, now) <= getCountBlock();

        if(!valid)
            playersKill.add(uuidPlayer);

        return valid;
    }

    @Override
    public synchronized void clearCache(){
        playersKill.removeMarks(
                System.currentTimeMillis()-(getTimeAnyPlayers()*1000)
        );
        mobsKill.removeMarks(
                System.currentTimeMillis()-(getTimeMob()*1000)
        );
        blockBreak.removeMarks(
                System.currentTimeMillis()-(getTimeBlock()*1000)
        );
    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {

        permissionAllow = section.getString("PermissionAllow");
        samePlayerCount = section.getInt("Player.SamePlayer.Count", -1);
        samePlayerTime = section.getInt("Player.SamePlayer.Time", -1);
        anyPlayerCount = section.getInt("Player.AnyPlayer.Count", -1);
        samePlayerTime = section.getInt("Player.AnyPlayer.Time",  -1);
        mobCount = section.getInt("Mob.Count", -1);
        mobTime = section.getInt("Mob.Time", -1);
        blockCount = section.getInt("Block.Count", -1);
        blockTime = section.getInt("Block.Time", -1);

    }

}
