package com.pixelmonessentials.common.commands;

import com.pixelmonessentials.common.guis.objectives.QuestSelectionGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandPEQuest extends CommandBase {
    public String getName(){
        return "pequest";
    }

    public String getUsage(ICommandSender sender){
        return "/pequest";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length==0){
            QuestSelectionGui gui=new QuestSelectionGui();
            gui.init((EntityPlayerMP)sender);
        }
        else{
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
