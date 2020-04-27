package com.pixelmonessentials;

import com.pixelmonessentials.common.api.gui.EssentialsGuis;
import com.pixelmonessentials.common.api.action.ActionHandler;
import com.pixelmonessentials.common.api.gui.EssentialsGuisHandler;
import com.pixelmonessentials.common.api.requirement.RequirementHandler;
import com.pixelmonessentials.common.commands.CommandEwand;
import com.pixelmonessentials.common.handler.GuiEventHandler;
import com.pixelmonessentials.common.handler.RightClickEventHandler;
import com.pixelmonessentials.common.teams.TeamManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import noppes.npcs.api.wrapper.WrapperNpcAPI;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

@Mod(
        modid="pixelmonessentials",
        name="Pixelmon Essentials",
        dependencies="required-after:pixelmon;required-after:customnpcs;",
        version ="1.0.1",
        acceptableRemoteVersions = "*",
        acceptedMinecraftVersions="[1.12.2]"
)
public class PixelmonEssentials {
    public static final String MOD_ID="pixelmonessentials";
    public static final String MOD_NAME="Pixelmon Essentials";
    public static final String VERSION="1.0.1";
    @Mod.Instance("pixelmonessentials")
    public static PixelmonEssentials INSTANCE;

    public static RequirementHandler requirementHandler=new RequirementHandler();
    public static TeamManager teamManager=new TeamManager();
    public static ActionHandler actionHandler=new ActionHandler();
    public static EssentialsGuisHandler essentialsGuisHandler=new EssentialsGuisHandler();

    public PixelmonEssentials(){
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        requirementHandler.init();
        actionHandler.init();
        essentialsGuisHandler.init();
        teamManager.init();
    }

    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new RightClickEventHandler());
        WrapperNpcAPI.EVENT_BUS.register(new GuiEventHandler());
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandEwand());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }
}
