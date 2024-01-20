package mod.jeje.voicerecognition.utils;

import java.util.ArrayList;
import java.util.List;

public class stringStuff {


    public static List<String> stringToList(String inputStr){
        return new ArrayList<>(List.of(inputStr.split(" ")));
    }

    public static String listToString(List<String> inputLst){
        StringBuilder resStr = new StringBuilder();
        for (String str : inputLst){
            if (resStr.isEmpty()){
                resStr.append(str);
            } else {
                resStr.append(" ").append(str);
            }
        }
        return resStr.toString();
    }
}
