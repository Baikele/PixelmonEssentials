package com.pixelmonessentials.common.commands;

import com.pixelmonessentials.common.util.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandEwand extends CommandBase {
    public String getName(){
        return "ewand";
    }

    public String getUsage(ICommandSender sender){
        return "/ewand [player]";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1){
            EntityPlayerMP playerMP=server.getPlayerList().getPlayerByUsername(args[0]);
            if(playerMP==null){
                throw new WrongUsageException(getUsage(sender), new Object[0]);
            }
            else{
                Item item = getItemByText(sender, "minecraft:wooden_hoe");
                ItemStack itemstack = new ItemStack(item);
                itemstack.getTagCompound().setBoolean("Unbreakable", true);
                itemstack.setStackDisplayName(Reference.resetText+"Essential Wand");
                playerMP.addItemStackToInventory(itemstack);
            }
        }
        else if(args.length==0){
            EntityPlayerMP player = getPlayer(server, sender, sender.getName());
            Item item = getItemByText(sender, "minecraft:wooden_hoe");
            ItemStack itemstack = new ItemStack(item);
            itemstack.setStackDisplayName(Reference.resetText+"Essential Wand");
            player.addItemStackToInventory(itemstack);
        }
        else{
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
