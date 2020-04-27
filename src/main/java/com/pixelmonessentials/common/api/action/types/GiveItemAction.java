package com.pixelmonessentials.common.api.action.types;

import com.pixelmonessentials.common.api.action.Action;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class GiveItemAction implements Action {
    public final String name="GIVEITEM";

    public String getName(){
        return this.name;
    }

    public void doAction(String value, EntityPlayerMP player){
        try {
            player.inventory.addItemStackToInventory(new ItemStack(JsonToNBT.getTagFromJson(value)));
        } catch (NBTException e) {
            e.printStackTrace();
        }
    }
}
