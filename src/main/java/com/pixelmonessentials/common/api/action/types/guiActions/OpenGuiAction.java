package com.pixelmonessentials.common.api.action.types.guiActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionIdData;
import com.pixelmonessentials.common.api.action.datatypes.ActionNpcGuiData;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.UUID;

public class OpenGuiAction extends ActionBase {
    public OpenGuiAction(){
        super("OPEN_GUI");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        if(data instanceof ActionNpcGuiData){
            EntityNPCInterface npc=((ActionNpcGuiData) data).getNpc();
            int id=((ActionNpcGuiData) data).getId();
            EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(((ActionIdData) data).getId());
            player.closeScreen();
            gui.init(player);
        }
        else if(data instanceof ActionIdData){
            EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(((ActionIdData) data).getId());
            player.closeScreen();
            gui.init(player);
        }
    }
}
