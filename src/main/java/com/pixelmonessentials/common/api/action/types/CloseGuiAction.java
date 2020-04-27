package com.pixelmonessentials.common.api.action.types;

import com.pixelmonessentials.common.api.action.Action;
import net.minecraft.entity.player.EntityPlayerMP;

public class CloseGuiAction implements Action {
    public final String name="CLOSE_GUI";

    public String getName(){
        return this.name;
    }

    public void doAction(String value, EntityPlayerMP playerMP){
        playerMP.closeScreen();
    }
}
