package br.neitan96.swordlevelv3.messages;

import br.neitan96.swordlevelv3.util.ConfigLoader;

/**
 * Created by neitan96 on 28/10/15.
 */
public interface SwordMessages extends ConfigLoader{

    String getPrefix();


    String getLevelup(int levelNow, int levelUp);

    String getXpReward(int xpWin, int xpMissing);

    String getViewLevel(int levelNow, int xpNow, int xpMissing);

}
