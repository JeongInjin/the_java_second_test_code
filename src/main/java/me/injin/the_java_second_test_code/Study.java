package me.injin.the_java_second_test_code;

public class Study {
    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

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
}
