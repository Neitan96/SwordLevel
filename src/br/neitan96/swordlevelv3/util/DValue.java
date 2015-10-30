package br.neitan96.swordlevelv3.util;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 20:45
 * Created by Neitan96 on 30/10/15.
 */
public class DValue<K, V>{

    protected K value1;
    protected V value2;

    public DValue(K value1, V value2){
        this.value1 = value1;
        this.value2 = value2;
    }

    public K getValue1(){
        return value1;
    }

    public V getValue2(){
        return value2;
    }

}
