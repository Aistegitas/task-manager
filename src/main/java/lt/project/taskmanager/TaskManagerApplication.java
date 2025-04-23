package lt.project.taskmanager;

import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.service.TaskService;
import lt.project.taskmanager.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor    //added for testing
@SpringBootApplication
public class TaskManagerApplication {
    private final TaskService taskService; //added for testing
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)   //added for testing
    public void test(){
        taskService.addTestTasks();
        //userService.addTestUsers();
    }
}
