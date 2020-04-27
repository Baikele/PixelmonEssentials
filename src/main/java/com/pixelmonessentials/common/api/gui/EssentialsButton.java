package com.pixelmonessentials.common.api.gui;

import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionData;

public class EssentialsButton {
    int id;
    ActionData[] actions;

    public EssentialsButton(int id, ActionData action){
        this.id=id;
        this.actions=new ActionData[]{action};
    }

    public EssentialsButton(int id, ActionData[] actions){
        this.id=id;
        this.actions=actions;
    }

    public int getId(){
        return this.id;
    }

    public ActionData getAction(){
        return this.actions[0];
    }

    public ActionData[] getActions(){
        return this.actions;
    }
}
