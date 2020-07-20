package com.pixelmonessentials.common.util;

public class DaytimeUtils {
    public enum EnumDayPhase{
        DAWN,
        MORNING,
        AFTERNOON,
        DUSK,
        NIGHT
    }

    public static EnumDayPhase getEnumFromName(String enumName){
        if(enumName.equalsIgnoreCase("DAWN")){
            return DaytimeUtils.EnumDayPhase.DAWN;
        }
        else if(enumName.equalsIgnoreCase("MORNING")){
            return DaytimeUtils.EnumDayPhase.MORNING;
        }
        else if(enumName.equalsIgnoreCase("AFTERNOON")){
            return DaytimeUtils.EnumDayPhase.AFTERNOON;
        }
        else if(enumName.equalsIgnoreCase("DUSK")){
            return DaytimeUtils.EnumDayPhase.DUSK;
        }
        else if(enumName.equalsIgnoreCase("NIGHT")){
            return DaytimeUtils.EnumDayPhase.NIGHT;
        }
        return EnumDayPhase.NIGHT;
    }

    public static String getNameFromEnum(EnumDayPhase phase){
        switch (phase){
            case DAWN:
                return "Dawn";
            case MORNING:
                return "Morning";
            case AFTERNOON:
                return "Afternoon";
            case DUSK:
                return "Dusk";
            case NIGHT:
                return "Night";
            default:
                return "";
        }
    }

    public static EnumDayPhase getPhaseFromTick(long time){
        if(time<6000){
            return EnumDayPhase.MORNING;
        }
        else if(time<12000){
            return EnumDayPhase.AFTERNOON;
        }
        else if(time<13000){
            return EnumDayPhase.DUSK;
        }
        else if(time<23000){
            return EnumDayPhase.NIGHT;
        }
        else {
            return EnumDayPhase.DAWN;
        }
    }
}
