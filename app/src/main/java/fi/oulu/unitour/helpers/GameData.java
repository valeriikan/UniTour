package fi.oulu.unitour.helpers;

/**
 * Created by Majid on 3/19/2017.
 */

public class GameData {
    public String loc01;
    public String loc02;
    public String loc03;
    public String loc04;
    public String loc05;
    public String loc06;
    public String loc07;
    public String loc08;
    public String loc09;
    public String loc10;
    public String loc11;
    public String loc12;
    public String loc13;
    public String loc14;
    public String loc15;
    public String loc16;

    public GameData()
    {
        loc01 = "0";
        loc02 = "0";
        loc03 = "0";
        loc04 = "0";
        loc05 = "0";
        loc06 = "0";
        loc07 = "0";
        loc08 = "0";
        loc09 = "0";
        loc10 = "0";
        loc11 = "0";
        loc12 = "0";
        loc13 = "0";
        loc14 = "0";
        loc15 = "0";
        loc16 = "0";
    }
    public boolean[] getLocStateArray()
    {
        boolean[] stateArray = new boolean[16];
        for (int i = 0; i <stateArray.length; i++)
            stateArray[i] = false;
        if (loc01 == "1") stateArray[0] = true;
        if (loc02 == "1") stateArray[1] = true;
        if (loc03 == "1") stateArray[2] = true;
        if (loc04 == "1") stateArray[3] = true;
        if (loc05 == "1") stateArray[4] = true;
        if (loc06 == "1") stateArray[5] = true;
        if (loc07 == "1") stateArray[6] = true;
        if (loc08 == "1") stateArray[7] = true;
        if (loc09 == "1") stateArray[8] = true;
        if (loc10 == "1") stateArray[9] = true;
        if (loc11 == "1") stateArray[10] = true;
        if (loc12 == "1") stateArray[11] = true;
        if (loc13 == "1") stateArray[12] = true;
        if (loc14 == "1") stateArray[13] = true;
        if (loc15 == "1") stateArray[14] = true;
        if (loc16 == "1") stateArray[15] = true;

        return stateArray;
    }
}
