package com.placementserver.placementserver.Schedule;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicTaskSchedulerService {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public DynamicTaskSchedulerService(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleTask(String taskId, LocalDateTime dateTime, Runnable taskLogic) {
        long delay = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();

        if (delay <= 0) {
            System.out.println("Invalid time! Task must be scheduled in the future.");
            return;
        }

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> {
            //System.out.println("Executing task at: " + LocalDateTime.now());
            // Add your task logic here (e.g., send an email, update the database)
            taskLogic.run();

        }, new java.util.Date(System.currentTimeMillis() + delay));

        scheduledTasks.put(taskId, scheduledFuture);
        //System.out.println("Task scheduled for: " + dateTime);
    }

    public void cancelTask(String taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null && !future.isDone()) {
            future.cancel(false);
            scheduledTasks.remove(taskId);
            System.out.println("Task " + taskId + " cancelled.");
        }
    }
}
