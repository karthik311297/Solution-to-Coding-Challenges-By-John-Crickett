package com.cchallenge.nats;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants
{
    public enum Commands
    {
        CONNECT,
        PING,
        PUB,
        SUB,
        UNSUB,
        CREATETOPIC,
        INVALID,
        STOP
    }
    
    public static final Set<String> validCommands = new HashSet<>(Arrays.asList(Commands.CONNECT.toString(),
            Commands.PING.toString(), Commands.PUB.toString(),
            Commands.SUB.toString(), Commands.CREATETOPIC.toString(),
            Commands.STOP.toString(), Commands.UNSUB.toString()));
    
}
