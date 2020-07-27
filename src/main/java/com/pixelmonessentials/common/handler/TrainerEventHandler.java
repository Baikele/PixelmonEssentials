package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.common.battles.CustomBattleHandler;
import com.pixelmonessentials.common.battles.CustomNPCBattle;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.comm.packetHandlers.PlayerDeath;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.event.DialogEvent;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.entity.EntityNPCInterface;

public class TrainerEventHandler {

    @SubscribeEvent
    public void onDialogClose(DialogEvent.CloseEvent event){
        ScriptObjectMirror object=NpcScriptDataManipulator.getJavascriptObject((NPCWrapper) event.npc, "trainerData");
        if(object!=null){
            if(event.dialog.getId()==(int)object.get("initDialog")){
                CustomBattleHandler.createCustomBattle(event.player.getMCEntity(), (EntityNPCInterface)event.npc.getMCEntity());
            }
            else if(event.dialog.getId()==(int)object.get("lossDialog")){
                event.player.removeDialog((int)object.get("initDialog"));
            }
        }
    }

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event){
        if(event.bc.rules instanceof CustomNPCBattle){
            BattleResults results=event.results.get(event.bc.participants.get(0));
            EntityPlayerMP mcPlayer = event.getPlayers().get(0);
            Pixelmon.instance.network.sendTo(new PlayerDeath(), mcPlayer);
            CustomNPCBattle battle = (CustomNPCBattle) event.bc.rules;
            BattleRegistry.deRegisterBattle(event.bc);
            if(results==BattleResults.VICTORY){
                NoppesUtilServer.openDialog(mcPlayer, battle.getNpc(), battle.getWinDialog());
            }
            else{
                NoppesUtilServer.openDialog(mcPlayer, battle.getNpc(), battle.getLoseDialog());
            }
        }
    }
}
