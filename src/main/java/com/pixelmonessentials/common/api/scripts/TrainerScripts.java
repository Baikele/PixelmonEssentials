package com.pixelmonessentials.common.api.scripts;

import com.pixelmonessentials.common.util.NpcScriptDataManipulator;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import noppes.npcs.api.event.DialogEvent;
import noppes.npcs.api.wrapper.NPCWrapper;

public class TrainerScripts {
    public static void battleFunction(DialogEvent.CloseEvent event){
        ScriptObjectMirror trainerData= NpcScriptDataManipulator.getJavascriptObject((NPCWrapper) event.npc, "trainerData");
        if(trainerData!=null){
            if(event.dialog.getId()==(int)trainerData.get("initDialog")){
            }
        }
    }
}
