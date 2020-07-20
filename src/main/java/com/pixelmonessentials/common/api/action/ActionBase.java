package com.pixelmonessentials.common.api.action;

import net.minecraft.entity.player.EntityPlayerMP;

public class ActionBase implements Action {
    private String name;

    public ActionBase(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void doAction(String value, EntityPlayerMP playerMP){
    }
}
