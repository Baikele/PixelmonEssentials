package com.pixelmonessentials.common.commands;

import com.pixelmonessentials.common.guis.battles.CustomRulesGui;
import com.pixelmonmod.pixelmon.comm.packetHandlers.OpenScreen;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandRules extends CommandBase {
    public String getName(){
        return "perules";
    }

    public String getUsage(ICommandSender sender){
        return "/perules";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length==0&&sender instanceof EntityPlayerMP){
            CustomRulesGui gui=new CustomRulesGui();
            gui.init((EntityPlayerMP) sender);
        }
        else {
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
