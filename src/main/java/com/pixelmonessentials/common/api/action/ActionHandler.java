package com.pixelmonessentials.common.api.action;

import com.pixelmonessentials.common.api.action.types.*;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;

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
        for(ActionData actionData: data){
            this.doAction(actionData, playerMP);
        }
    }

    public void addAction(Action action){
        this.actionTypes.add(action);
    }
}
