package lt.project.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.project.taskmanager.dto.UpdateSubtaskRequest;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.repository.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskService taskService;

    public List<Subtask> getAllSubtasks() {
        log.debug("Fetching all subtasks");
        return subtaskRepository.findAll();
    }

    public Subtask getSubtaskByIdOrThrow(Integer id) {
        log.debug("Fetching subtask with id: {}", id);
        return subtaskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subtask with id: {} not found", id);
                    return new EntityNotFoundException("Subtask with id: " + id + " not found");
                });
    }

    public Subtask addSubtask(Subtask subtask) {
        return subtaskRepository.saveAndFlush(subtask);
    }

    public Subtask updateSubtask(Integer id, UpdateSubtaskRequest request) {
        Subtask subtask = getSubtaskByIdOrThrow(id);
        if (request.getTitle() != null) subtask.setTitle(request.getTitle());
        if (request.getDescription() != null) subtask.setDescription(request.getDescription());
        if (request.getStatus() != null) subtask.setStatus(request.getStatus());

        Subtask updatedSubtask = subtaskRepository.saveAndFlush(subtask);
        log.info("Successfully updated subtask with id: {}", updatedSubtask.getId());
        return updatedSubtask;
    }

    public void deleteSubtask(Integer id) {
        log.info("Attempting to delete subtask with id: {}", id);
        if (!subtaskRepository.existsById(id)) {
            log.warn("Subtask with id: {} not found", id);
            throw new EntityNotFoundException("Subtask with id: " + id + " not found");
        }
        subtaskRepository.deleteById(id);
        log.info("Successfully deleted subtask with id: {}", id);
    }
}

