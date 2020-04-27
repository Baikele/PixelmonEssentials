package com.pixelmonessentials.common.handler;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.guis.NpcHomeGui;
import com.pixelmonessentials.common.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class RightClickEventHandler {
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.EntityInteract event){
        ItemStack heldItem=event.getEntityPlayer().getHeldItemMainhand();
        if((heldItem.getItem().getRegistryName()+"").equals("minecraft:wooden_hoe")){
            if(heldItem.getDisplayName().equals(Reference.resetText+"Essential Wand")){
                if(event.getTarget() instanceof EntityCustomNpc){
                    event.setCanceled(true);
                    PixelmonEssentials.essentialsGuisHandler.getGui(1000).init((EntityPlayerMP) event.getEntityPlayer(), (EntityNPCInterface) event.getTarget());
                }
            }
        }
    }
}
