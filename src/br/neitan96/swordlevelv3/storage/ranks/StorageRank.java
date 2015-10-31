package br.neitan96.swordlevelv3.storage.ranks;

import br.neitan96.swordlevelv3.util.ConfigLoader;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 31/Out/2015 03:03
 * Created by Neitan96 on 31/10/15.
 */
public interface StorageRank extends ConfigLoader{

    void updateScore(String player, int levelWin, int xpWin, int levelNow, int xpNow);

}
