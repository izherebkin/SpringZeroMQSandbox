package org.izherebkin.integration.task;

import org.springframework.scheduling.Trigger;

public interface LoopTask extends Runnable {

    Trigger getTrigger();

}
