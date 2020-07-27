package com.pixelmonessentials.common.api.action.datatypes;

import com.pixelmonessentials.common.api.action.ActionData;
import noppes.npcs.entity.EntityNPCInterface;

public class ActionNpcGuiData extends ActionIdData {
    EntityNPCInterface npc;

    public ActionNpcGuiData(String name, EntityNPCInterface npc, int id){
        super(name, id);
        this.npc=npc;
    }

    public EntityNPCInterface getNpc(){
        return this.npc;
    }
}
