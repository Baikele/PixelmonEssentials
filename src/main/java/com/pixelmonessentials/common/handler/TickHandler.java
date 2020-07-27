package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.common.util.EssentialsLogger;
import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.HashMap;
import java.util.Map;

public class TickHandler {
    public static Map<NPCWrapper, Integer> battleNPCs = new HashMap();

    @SubscribeEvent
    public static void onNpcInit(NpcEvent.InitEvent event){
        Object object= NpcScriptDataManipulator.getJavascriptVariable((NPCWrapper) event.npc, "losDialog");
        if(object!=null){
            if(battleNPCs.containsKey((NPCWrapper)event.npc)){
                battleNPCs.replace((NPCWrapper)event.npc, (int)object);
            }
            else{
                battleNPCs.put((NPCWrapper) event.npc, (int)object);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if(!battleNPCs.isEmpty()) {
            for(NPCWrapper npc : battleNPCs.keySet()) {
                if(npc!=null) {
                    raytraceNPCBattle(npc, battleNPCs.get(npc));
                }
            }
        }
    }

    static void raytraceNPCBattle(NPCWrapper npc, int initDialogID) {
        IEntity[] losEntities = npc.rayTraceEntities(5, true, true);
        if(losEntities.length > 0) {
            for(IEntity e : losEntities) {
                if(e instanceof PlayerWrapper) {
                    PlayerWrapper p = (PlayerWrapper)e;
                    if(!p.hasReadDialog(initDialogID) && !(p.getGamemode()==1 || p.getGamemode()==3)) {
                        NoppesUtilServer.openDialog((EntityPlayerMP) p.getMCEntity(), (EntityNPCInterface) npc.getMCEntity(), (Dialog) DialogController.instance.get(initDialogID));
                    }
                }
            }
        }
    }
}
