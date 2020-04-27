package com.pixelmonessentials.common.api.action.types;

import com.pixelmonessentials.common.api.action.Action;
import net.minecraft.entity.player.EntityPlayerMP;

public class SaveTrainerAction implements Action {
    public final String name="SAVE_TRAINER";

    public String getName(){return this.name;}

    public void doAction(String value, EntityPlayerMP playerMP){
    }
}
