package com.pixelmonessentials.common.api.action.types.generalActions;

import com.pixelmonessentials.common.api.action.Action;
import com.pixelmonessentials.common.api.action.ActionBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class GiveItemAction extends ActionBase {
    public GiveItemAction(){
        super("GIVEITEM");
    }

    @Override
    public void doAction(String value, EntityPlayerMP player){
        try {
            player.inventory.addItemStackToInventory(new ItemStack(JsonToNBT.getTagFromJson(value)));
        } catch (NBTException e) {
            e.printStackTrace();
        }
    }
}
