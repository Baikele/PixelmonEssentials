package com.pixelmonessentials.common.util;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeObject;
import jdk.nashorn.internal.runtime.ScriptObject;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityCustomNpc;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;

public class NpcScriptDataManipulator {
    public static ScriptObjectMirror getJavascriptObject(NPCWrapper npc, String variable, int page){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(page));
            return (ScriptObjectMirror)engine.getContext().getAttribute(variable);
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
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            return (ScriptObjectMirror)engine.getContext().getAttribute(variable);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ScriptObjectMirror createJavascriptObject(NPCWrapper npc, String variable){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            ScriptEngineManager m = new ScriptEngineManager();
            ScriptEngine e = m.getEngineByName("nashorn");
            JSObject object = (JSObject)e.eval("Object");
            engine.put(variable, ScriptUtils.wrap(object));
            return (ScriptObjectMirror)engine.getContext().getAttribute(variable);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setJavascriptObject(NPCWrapper npc, String variable, String objectString){
        Field f = null;
        try {
            f = ScriptContainer.class.getDeclaredField("engine");
            f.setAccessible(true);
            ScriptEngine engine = (ScriptEngine)f.get(((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0));
            ScriptContainer scriptContainer=((EntityCustomNpc)npc.getMCEntity()).script.getScripts().get(0);
            f.set(scriptContainer, engine);
            if(scriptContainer.script.contains("var "+variable)){
                String variableContent=scriptContainer.script.split("var "+variable+"=")[1].split("};")[0];
                scriptContainer.script=scriptContainer.script.replace("var "+variable+"="+variableContent+"};", "");
            }
            scriptContainer.script+="\nvar "+variable+"="+objectString;
            ((EntityCustomNpc)npc.getMCEntity()).script.getScripts().set(0, scriptContainer);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
