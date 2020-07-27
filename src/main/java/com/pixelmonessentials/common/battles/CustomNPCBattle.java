package com.pixelmonessentials.common.battles;

import com.pixelmonessentials.common.api.data.TrainerNPCData;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.entity.EntityNPCInterface;

public class CustomNPCBattle extends BattleRules {
    private Dialog winDiag;
    private Dialog loseDiag;
    private Dialog initDiag;
    private int remainingNPCPokemon;
    public static EntityPlayer player;
    private static EntityNPCInterface npc;

    public CustomNPCBattle(EntityNPCInterface npc, BattleRules rules){
        super();
        this.npc=npc;
        this.importText(rules.exportText());
        ScriptObjectMirror object= NpcScriptDataManipulator.getJavascriptObject(new NPCWrapper(npc), "trainerData");
        this.initDiag=DialogController.instance.dialogs.get((int)object.get("initDialog"));
        this.winDiag=DialogController.instance.dialogs.get((int)object.get("winDialog"));
        this.loseDiag=DialogController.instance.dialogs.get((int)object.get("lossDialog"));
    }

    public CustomNPCBattle(EntityNPCInterface npc)
    {
        super();
        this.npc=npc;
        ScriptObjectMirror object= NpcScriptDataManipulator.getJavascriptObject(new NPCWrapper(npc), "trainerData");
        this.initDiag=DialogController.instance.dialogs.get((int)object.get("initDialog"));
        this.winDiag=DialogController.instance.dialogs.get((int)object.get("winDialog"));
        this.loseDiag=DialogController.instance.dialogs.get((int)object.get("lossDialog"));
    }

    public void addWinDialog(Dialog winDiag)
    {
        this.winDiag=winDiag;
    }

    public void addLoseDialog(Dialog loseDiag)
    {
        this.loseDiag=loseDiag;
    }

    public Dialog getWinDialog()
    {
        return this.winDiag;
    }

    public Dialog getLoseDialog()
    {
        return this.loseDiag;
    }

    public Dialog getInitDiag() {return this.initDiag;}

    public EntityNPCInterface getNpc()
    {
        return this.npc;
    }

    public int getRemainingNPCPokemon(){return this.remainingNPCPokemon;}

    public void setRemainingNPCPokemon(int count){this.remainingNPCPokemon=count;}

    public boolean hasPlayer(EntityPlayer participant)
    {
        if(participant==player)
            return true;
        else
            return false;
    }
}
