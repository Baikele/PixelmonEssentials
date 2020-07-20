package com.pixelmonessentials.common.commands;

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

public class CommandEspawner extends CommandBase {
    public String getName(){
        return "pespawner";
    }

    public String getUsage(ICommandSender sender){
        return "/pespawner [x] [y] [z]";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length==0&&sender instanceof EntityPlayerMP){
            SpawnerInfoGui spawnerInfoGui=new SpawnerInfoGui();
            spawnerInfoGui.init((EntityPlayerMP) sender);
        }
        else if(args.length==3&&sender instanceof EntityPlayerMP){
            try{
                int x=Integer.parseInt(args[0]);
                int y=Integer.parseInt(args[1]);
                int z=Integer.parseInt(args[2]);
                BlockPos pos=new BlockPos(x, y, z);
                IBlockState block=sender.getEntityWorld().getBlockState(pos);
                if(block.getBlock().getRegistryName().toString().equals("pixelmon:pixelmon_spawner")){
                    TileEntityPixelmonSpawner spawner = BlockHelper.getTileEntity(TileEntityPixelmonSpawner.class, sender.getEntityWorld(), pos);
                    ((EntityPlayerMP)sender).connection.sendPacket(spawner.getUpdatePacket());
                    spawner.onActivate();
                    OpenScreen.open(((EntityPlayerMP)sender), EnumGuiScreen.PixelmonSpawner, new int[]{pos.getX(), pos.getY(), pos.getZ()});
                }
            }catch (NumberFormatException e){
                throw new WrongUsageException(getUsage(sender), new Object[0]);
            }
        }
        else {
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
