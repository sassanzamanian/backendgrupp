package se.group.backendgruppuppgift.tasker.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import se.group.backendgruppuppgift.tasker.model.Issue;
import se.group.backendgruppuppgift.tasker.model.Task;
import se.group.backendgruppuppgift.tasker.model.TaskStatus;
import se.group.backendgruppuppgift.tasker.model.web.IssueWeb;
import se.group.backendgruppuppgift.tasker.model.web.TaskWeb;
import se.group.backendgruppuppgift.tasker.repository.IssueRepository;
import se.group.backendgruppuppgift.tasker.repository.TaskRepository;
import se.group.backendgruppuppgift.tasker.service.exception.IssueException;

import java.util.Optional;

@Service
public final class TaskService {

    private final TaskRepository repository;
    private final IssueRepository issueRepository;

    public TaskService(TaskRepository repository, IssueRepository issueRepository) {
        this.repository = repository;
        this.issueRepository = issueRepository;
    }

    public Task createTask(TaskWeb task) {
        System.out.println(task); // TODO: 2018-05-09 Remove in final stage of development
        return repository.save(new Task(task.getName(), task.getStatus()));
    }

    public Optional<Task> getTask(Long id) {

        return repository.findById(id);
    }

    public Optional<Task> updateTask(Long id, TaskWeb task) {
        Optional<Task> result = repository.findById(id);
        System.out.println(task);
        if (result.isPresent()) {
            Task updatedTask = result.get();

            if (!StringUtils.isBlank(task.getName())) {
                updatedTask.setName(task.getName());
            }
            if (!StringUtils.isBlank(task.getStatus().toString())) {
                updatedTask.setStatus(task.getStatus());
            }

            System.out.println(updatedTask);

            return Optional.ofNullable(repository.save(updatedTask));
        }

        return Optional.empty();
    }

    public Issue createIssue(Issue issue, Long id) {
        validateWorkItemStatusAndSetStatus(id);
        return issueRepository.save(issue);
    }

    public Optional<Issue> updateIssue(Long id, IssueWeb issue) {
        Optional<Issue> result = issueRepository.findById(id);

        if (result.isPresent()) {
            Issue updatedIssue = result.get();

            if (!StringUtils.isBlank(issue.getDescription())) {
                updatedIssue.setDescription(issue.getDescription());
            }
            if (issue.getIsDone() != null) {
                updatedIssue.setIsDone(issue.getIsDone());
            }

            return Optional.ofNullable(issueRepository.save(updatedIssue));

        }
        return Optional.empty();
    }

    private void validateWorkItemStatusAndSetStatus(Long id) {
        Optional<Task> result = repository.findById(id);
        Task task = result.get();

        if (!(task.getStatus() == TaskStatus.DONE)) {
            throw new IssueException("The current Task's status is not DONE");
        } else {
            task.setStatus(TaskStatus.UNSTARTED);
        }
    }
}
