package org.task.robots.bot.implementation;

import org.task.robots.bot.AbstractBot;

import java.util.concurrent.BlockingQueue;

import static org.task.robots.enums.BotType.SLOW;

public class SlowBot extends AbstractBot {
    public SlowBot(BlockingQueue<Runnable> taskQueue, String name) {
        super(taskQueue, name);
        this.type = SLOW;
    }

    @Override
    public void run() {
        super.run();
    }
}
