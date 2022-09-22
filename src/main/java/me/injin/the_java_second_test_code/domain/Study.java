package me.injin.the_java_second_test_code.domain;

import lombok.Getter;
import lombok.Setter;
import me.injin.the_java_second_test_code.study.StudyStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class Study {
    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;
    private String name;
    private LocalDateTime openedDateTime;

    private Member owner;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        if(limit < 0) throw new IllegalArgumentException("limit 은 0 보다 커야 합니다.");
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}
