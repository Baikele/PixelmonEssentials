package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.common.battles.CustomNPCBattle;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectionList;

import java.util.TimerTask;

public class TeamPreviewTimerTask extends TimerTask {
    CustomNPCBattle battle;
    PartyStorage[] partyStorage;

    public TeamPreviewTimerTask(CustomNPCBattle battle, PartyStorage[] partyStorage){
        this.battle=battle;
        this.partyStorage=partyStorage;
    }

    @Override
    public void run(){
        TeamSelectionList.addTeamSelection(battle, false, partyStorage);
    }
}
