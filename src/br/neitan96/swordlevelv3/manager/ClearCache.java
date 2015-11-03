package br.neitan96.swordlevelv3.manager;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 03/Nov/2015 04:12
 * Created by Neitan96 on 03/11/15.
 */
public class ClearCache implements Runnable{

    protected final GroupManager manager;

    public ClearCache(GroupManager manager){
        this.manager = manager;
    }

    @Override
    public void run(){
        for (Group group : manager.getGroupList())
            if(group.getAntiTheft() != null)
                group.getAntiTheft().clearCache();
    }
}
