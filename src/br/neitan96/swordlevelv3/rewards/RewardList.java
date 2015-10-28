package br.neitan96.swordlevelv3.rewards;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by neitan96 on 28/10/15.
 */
public interface RewardList extends ConfigLoader{

    Map<Integer, List<String>> getRewards();

    void sendRewards(Player player, int level);

}