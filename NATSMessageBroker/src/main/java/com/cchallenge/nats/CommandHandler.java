package com.cchallenge.nats;

import com.cchallenge.messaging.model.Message;
import com.cchallenge.messaging.mq.MessageQueue;
import com.cchallenge.nats.Constants.Commands;

public class CommandHandler
{
    public static String handleCommand(String command, int subscriberID)
    {
        String[] splitCommand = command.split(" ");
        Commands cmd = getCmd(splitCommand);
        switch(cmd)
        {
            case PING:
                return "PONG";
            case CREATETOPIC:
                TopicsManager.getInstance().createNewTopic(splitCommand[1]);
                return "+OK";
            case SUB:
                MessageQueue.getInstance().subscribeToTopic(splitCommand[1],
                        SubscribersManager.getInstance().getSubscriber(subscriberID));
                return "+OK";
            case PUB:
                MessageQueue.getInstance().publishMessageToTopic(splitCommand[1], new Message(splitCommand[2]));
                return "+OK";
            case UNSUB:
                MessageQueue.getInstance().unsubscribeToTopic(splitCommand[1],
                        SubscribersManager.getInstance().getSubscriber(subscriberID));
                return "+OK, Unsubscribed from the topic";
            default:
                return "Invalid command";
        }
    }
    
    private static Commands getCmd(String[] splitCommand)
    {
        String theCommand = splitCommand[0].toUpperCase();
        if(!Constants.validCommands.contains(theCommand))
        {
            return Commands.INVALID;
        }
        return Commands.valueOf(theCommand);
    }
}
