package com.pixelmonessentials.common.util;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeObject;
import jdk.nashorn.internal.runtime.ScriptObject;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.Server;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.api.wrapper.WrapperNpcAPI;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;

public class NpcScriptDataManipulator {
    public static Object getJavascriptVariable(NPCWrapper npc, String variable){
        Field f = null;
        try {
            if(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().size()>0) {
                f = ScriptContainer.class.getDeclaredField("engine");
                f.setAccessible(true);
                ScriptEngine engine = (ScriptEngine) f.get(((EntityCustomNpc) npc.getMCEntity()).script.getScripts().get(0));
                return engine.get(variable);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
        }
        return null;
    }

    public static ScriptObjectMirror getJavascriptObject(NPCWrapper npc, String variable, int page){
        Field f = null;
        try {
            if(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().size()>page){
                f = ScriptContainer.class.getDeclaredField("engine");
                f.setAccessible(true);
                ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(page));
                return (ScriptObjectMirror)engine.getContext().getAttribute(variable);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ScriptObjectMirror getJavascriptObject(NPCWrapper npc, String variable){
        Field f = null;
        try {
            if(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().size()>0){
                f = ScriptContainer.class.getDeclaredField("engine");
                f.setAccessible(true);
                ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
                return (ScriptObjectMirror)engine.getContext().getAttribute(variable);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
        }
        return null;
    }

    public static void setJavascriptObject(NPCWrapper npc, String variable, String objectString){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            if(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().size()==0){
                ((EntityCustomNpc)npc.getMCEntity()).script.getScripts().add(new ScriptContainer(((EntityNPCInterface)npc.getMCEntity()).script));
            }
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            ScriptContainer scriptContainer=((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0);
            f.set(scriptContainer, engine);
            if(scriptContainer.script.contains("var "+variable)){
                String variableContent=scriptContainer.script.split("var "+variable+"=")[1].split("};")[0];
                scriptContainer.script=scriptContainer.script.replace("var "+variable+"="+variableContent+"};", "");
            }
            scriptContainer.script+="var "+variable+"="+objectString;
            ((EntityCustomNpc)npc.getMCEntity()).script.getScripts().set(0, scriptContainer);
            ((EntityCustomNpc) npc.getMCEntity()).script.setEnabled(true);
            ((EntityCustomNpc) npc.getMCEntity()).script.readFromNBT(((EntityCustomNpc)npc.getMCEntity()).script.writeToNBT(new NBTTagCompound()));
            WrapperNpcAPI.EVENT_BUS.post(new NpcEvent.InitEvent(npc));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setJavascriptVariable(NPCWrapper npc, String variable, Object value){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            if(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().size()==0){
                ((EntityCustomNpc)npc.getMCEntity()).script.getScripts().add(new ScriptContainer(((EntityCustomNpc) npc.getMCEntity()).script));
            }
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            ScriptContainer scriptContainer=((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0);
            f.set(scriptContainer, engine);
            if(scriptContainer.script.contains("var "+variable)){
                String variableContent=scriptContainer.script.split("var "+variable+"=")[1].split(";")[0];
                scriptContainer.script=scriptContainer.script.replace("var "+variable+"="+variableContent+";", "");
            }
            if(value instanceof String){
                value="\""+value+"\"";
            }
            scriptContainer.script+="var "+variable+"="+value+";";
            ((EntityCustomNpc)npc.getMCEntity()).script.getScripts().set(0, scriptContainer);
            ((EntityCustomNpc) npc.getMCEntity()).script.setEnabled(true);
            ((EntityCustomNpc) npc.getMCEntity()).script.readFromNBT(((EntityCustomNpc)npc.getMCEntity()).script.writeToNBT(new NBTTagCompound()));
            WrapperNpcAPI.EVENT_BUS.post(new NpcEvent.InitEvent(npc));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void deleteJavascriptVariable(NPCWrapper npc, String variable){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            ScriptContainer scriptContainer=((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0);
            f.set(scriptContainer, engine);
            if(scriptContainer.script.contains("var "+variable)){
                String variableContent=scriptContainer.script.split("var "+variable+"=")[1].split(";")[0];
                scriptContainer.script=scriptContainer.script.replace("var "+variable+"="+variableContent+";", "");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
