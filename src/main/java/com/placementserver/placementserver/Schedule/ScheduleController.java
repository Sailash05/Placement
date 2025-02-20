package com.placementserver.placementserver.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final DynamicTaskSchedulerService schedulerService;

    public ScheduleController(DynamicTaskSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/task")
    public ResponseEntity<String> scheduleTask(@RequestParam String taskId, @RequestParam String dateTime) {
        LocalDateTime scheduleTime = LocalDateTime.parse(dateTime);
        //schedulerService.scheduleTask(taskId, scheduleTime);
        return ResponseEntity.ok("Task scheduled for: " + scheduleTime);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<String> cancelTask(@PathVariable String taskId) {
        schedulerService.cancelTask(taskId);
        return ResponseEntity.ok("Task " + taskId + " cancelled.");
    }
}
