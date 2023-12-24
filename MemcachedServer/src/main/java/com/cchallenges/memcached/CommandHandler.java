package com.cchallenges.memcached;

import java.util.Arrays;
import java.util.HashSet;

public class CommandHandler
{
    public static String handleCommand(String command)
    {
        String[] splitCommand = command.split(" ");
        Commands cmd = getCmd(splitCommand);
        switch(cmd)
        {
            case GET:
                return String.format("The value of this key is %s", CacheManager.getInstance().getVal(splitCommand[1]));
            case PUT:
                CacheManager.getInstance().putKeyVal(splitCommand[1], splitCommand[2]);
                return "Successfully added the key value pair into cache";
            case REMOVE:
                CacheManager.getInstance().removeKey(splitCommand[1]);
                return "Successfully removed the key value pair from cache";
            default:
                return "Invalid command";
        }
    }
    
    private static Commands getCmd(String[] splitCommand)
    {
        String theCommand = splitCommand[0].toUpperCase();
        if(!new HashSet<>(Arrays.asList(Commands.PUT.toString(),
                Commands.GET.toString(), Commands.REMOVE.toString())).contains(theCommand))
        {
            return Commands.INVALID;
        }
        return Commands.valueOf(theCommand);
    }
}
