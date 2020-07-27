package com.pixelmonessentials.common.api.action.types.generalActions;

import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import com.pixelmonessentials.common.api.action.ActionData;
import com.pixelmonessentials.common.api.action.datatypes.ActionStringData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class GiveItemAction extends ActionBase {
    public GiveItemAction(){
        super("GIVEITEM");
    }

    @Override
    public void doAction(EntityPlayerMP player, ActionData data){
        if(data instanceof ActionStringData){
            try {
                player.inventory.addItemStackToInventory(new ItemStack(JsonToNBT.getTagFromJson(((ActionStringData) data).getValue())));
            } catch (NBTException e) {
                e.printStackTrace();
            }
        }
    }
}
