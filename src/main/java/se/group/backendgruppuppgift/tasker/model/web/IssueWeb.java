package se.group.backendgruppuppgift.tasker.model.web;

import se.group.backendgruppuppgift.tasker.model.TaskStatus;

public final class IssueWeb {

    private String description;
    private Boolean isDone;

    protected IssueWeb() {
    }

    public IssueWeb(String name, TaskStatus status) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getDescription() {

        return description;
    }

    public Boolean getIsDone() {

        return isDone;
    }


}
