package org.task.robots.bot;

import org.task.robots.bot.implementation.FastBot;
import org.task.robots.bot.implementation.SlowBot;
import org.task.robots.enums.BotType;

import java.util.concurrent.BlockingQueue;

public class BotFactory {

    public static AbstractBot createBot(BotType type, BlockingQueue<Runnable> taskQueue, String name) {
        return switch (type) {
            case FAST -> new FastBot(taskQueue, name);
            case SLOW -> new SlowBot(taskQueue, name);
        };
    }
}
