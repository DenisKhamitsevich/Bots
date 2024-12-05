package org.task.robots.bot.implementation;

import org.task.robots.bot.AbstractBot;

import java.util.concurrent.BlockingQueue;

import static org.task.robots.enums.BotType.FAST;

public class FastBot extends AbstractBot {
    public FastBot(BlockingQueue<Runnable> taskQueue, String name) {
        super(taskQueue, name);
        this.type = FAST;
    }
}
