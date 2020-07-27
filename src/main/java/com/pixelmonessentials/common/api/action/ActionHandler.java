package com.pixelmonessentials.common.api.action;

import com.pixelmonessentials.common.api.action.types.*;
import com.pixelmonessentials.common.api.action.types.generalActions.CommandAction;
import com.pixelmonessentials.common.api.action.types.generalActions.GiveItemAction;
import com.pixelmonessentials.common.api.action.types.guiActions.CloseGuiAction;
import com.pixelmonessentials.common.api.action.types.guiActions.OpenGuiAction;
import com.pixelmonessentials.common.api.action.types.questActions.ClearObjectiveAction;
import com.pixelmonessentials.common.api.action.types.questActions.OpenObjectiveAction;
import com.pixelmonessentials.common.api.action.types.questActions.OpenQuestAction;
import com.pixelmonessentials.common.api.action.types.questActions.OpenQuestGuiAction;
import com.pixelmonessentials.common.api.action.types.rulesActions.EditRuleAction;
import com.pixelmonessentials.common.api.action.types.rulesActions.NewRuleAction;
import com.pixelmonessentials.common.api.action.types.rulesActions.ReloadRulesAction;
import com.pixelmonessentials.common.api.action.types.rulesActions.RemoveRuleAction;
import com.pixelmonessentials.common.api.action.types.spawnerActions.*;
import com.pixelmonessentials.common.api.action.types.trainerActions.*;
import com.pixelmonessentials.common.handler.ActionTimerTask;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Timer;

public class ActionHandler {
    ArrayList<Action> actionTypes=new ArrayList<Action>();

    public void init(){
        actionTypes.add(new AddSpawnDataAction());
        actionTypes.add(new DeleteSpawnDataAction());
        actionTypes.add(new CreateSpeciesAction());
        actionTypes.add(new EditSpeciesAction());
        actionTypes.add(new DeleteSpeciesAction());
        actionTypes.add(new SaveSpeciesAction());
        actionTypes.add(new EditTimesAction());
        actionTypes.add(new ClearTimesAction());
        actionTypes.add(new ReturnIndividualSpawnAction());
        actionTypes.add(new CancelTimesAction());

        actionTypes.add(new OpenQuestAction());
        actionTypes.add(new OpenObjectiveAction());
        actionTypes.add(new ClearObjectiveAction());
        actionTypes.add(new OpenQuestGuiAction());

        actionTypes.add(new NewRuleAction());
        actionTypes.add(new EditRuleAction());
        actionTypes.add(new RemoveRuleAction());
        actionTypes.add(new ReloadRulesAction());

        actionTypes.add(new EditTrainerAction());
        actionTypes.add(new SelectTeamAction());
        actionTypes.add(new ReturnToTeamAction());
        actionTypes.add(new SetShownPokemonAction());
        actionTypes.add(new ReturnToTrainerAction());
        actionTypes.add(new SaveTeamAction());
        actionTypes.add(new SelectRulesAction());
        actionTypes.add(new ClearRulesAction());
        actionTypes.add(new SaveRulesAction());
        actionTypes.add(new CancelRulesAction());
        actionTypes.add(new FlipLosAction());
        actionTypes.add(new OtherSettingsAction());

        actionTypes.add(new CommandAction());
        actionTypes.add(new GiveItemAction());
        actionTypes.add(new OpenGuiAction());
        actionTypes.add(new CloseGuiAction());
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
        action.doAction(playerMP, data);
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
