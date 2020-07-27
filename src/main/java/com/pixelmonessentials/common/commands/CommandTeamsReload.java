package com.pixelmonessentials.common.commands;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.guis.spawners.SpawnerInfoGui;
import com.pixelmonmod.pixelmon.blocks.spawning.TileEntityPixelmonSpawner;
import com.pixelmonmod.pixelmon.comm.packetHandlers.OpenScreen;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import com.pixelmonmod.pixelmon.util.helpers.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

public class CommandTeamsReload extends CommandBase {
    public String getName(){
        return "peteams";
    }

    public String getUsage(ICommandSender sender){
        return "/peteams reload";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length==1&&sender instanceof EntityPlayerMP){
            if(args[0].equalsIgnoreCase("reload")){
                try {
                    PixelmonEssentials.teamManager.loadCategories();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
