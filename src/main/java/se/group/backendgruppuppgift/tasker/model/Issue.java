package se.group.backendgruppuppgift.tasker.model;

import javax.persistence.*;

@Entity
public final class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne(mappedBy = "issue")
    private Task task;

    @Column(nullable = false)
    private boolean isDone;

    protected Issue() {
    }

    public Issue(String description, Task task) {
        this.description = description;
        this.task = task;
        this.isDone = false;
    }

    public Long getId() {

        return id;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {

        return task;
    }

    public void setTask(Task task) {ko
        this.task = task;
    }

    public boolean getIsDone() {

        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }
}
