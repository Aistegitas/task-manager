package lt.project.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;

    public List<Subtask> getAllSubtasks() {
        log.debug("Fetching all subtasks");
        return subtaskRepository.findAll();
    }
}
