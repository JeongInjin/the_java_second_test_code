package me.injin.the_java_second_test_code.study;

import me.injin.the_java_second_test_code.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
