package com.pixelmonessentials.common.api.action;

import com.pixelmonessentials.common.api.action.types.*;
import com.pixelmonessentials.common.handler.ActionTimerTask;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Timer;

public class ActionHandler {
    ArrayList<Action> actionTypes=new ArrayList<Action>();

    public void init(){
        actionTypes.add(new CommandAction());
        actionTypes.add(new GiveItemAction());
        actionTypes.add(new OpenGuiAction());
        actionTypes.add(new CloseGuiAction());
        actionTypes.add(new SaveTrainerAction());
    }

    public Action getType(String type){
        for(Action actionType: this.actionTypes){
            if(actionType.getName().equals(type)){
                return actionType;
            }
        }
        return null;
    }

    public Action getType(ActionData type){
        for(Action actionType: this.actionTypes){
            if(actionType.getName().equals(type.name)){
                return actionType;
            }
        }
        return null;
    }

    public void doAction(ActionData data, EntityPlayerMP playerMP){
        Action action=getType(data);
        if(data.closeInv)
            playerMP.closeScreen();
        action.doAction(data.value, playerMP);
    }

    public void doActions(ActionData[] data, EntityPlayerMP playerMP){
        Timer timer=new Timer();
        for(int i=0;i<data.length;i++){
            timer.schedule(new ActionTimerTask(data[i], playerMP), 100*i);
        }
    }

    public void addAction(Action action){
        this.actionTypes.add(action);
    }
}
