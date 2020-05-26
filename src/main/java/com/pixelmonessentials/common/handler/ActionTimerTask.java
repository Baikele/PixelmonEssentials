package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.ActionData;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.TimerTask;

public class ActionTimerTask extends TimerTask {
    private ActionData actionData;
    private EntityPlayerMP player;

    public ActionTimerTask(ActionData action, EntityPlayerMP player){
        this.actionData=action;
        this.player=player;
    }

    @Override
    public void run(){
        PixelmonEssentials.actionHandler.doAction(this.actionData, this.player);
    }
}
