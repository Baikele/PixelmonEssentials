package com.pixelmonessentials.common.api.action.types.guiActions;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.UUID;

public class OpenGuiAction extends ActionBase {
    public OpenGuiAction(){
        super("OPEN_GUI");
    }

    @Override
    public void doAction(String value, EntityPlayerMP player){
        String[] values=value.split("@");
        EntityNPCInterface npc=null;
        if(!values[0].equals("null")) {
            UUID uuid = UUID.fromString(values[0]);
            npc = (EntityNPCInterface) player.getServerWorld().getEntityFromUuid(uuid);
        }
        int id=Integer.parseInt(values[1]);
        EssentialsGuis gui= PixelmonEssentials.essentialsGuisHandler.getGui(id);
        player.closeScreen();
        gui.init(player);
    }
}
