package me.injin.the_java_second_test_code;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("static 메서드여야하고 반환값이 없어야함, 테스트 시작 전 1번만 실행 beforeAll");
    }

    @AfterAll
    static void AfterAll() {
        System.out.println("static 메서드여야하고 반환값이 없어야함, 테스트 시작 후 1번만 실행 AfterAll");
    }

    @BeforeEach
    public void BeforeEach() {
        System.out.println("테스트 시작 전 실행 -> BeforeEach");
    }

    @AfterEach
    public void AfterEach() {
        System.out.println("테스트 종료 후 실행 -> AfterEach");
    }

    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE0E")
    public void create_new_study() {
        Study study = new Study(10);
        assertThat(study).isNotNull();

        //블록 안에 있는 테스는 실패 여부와 상관없이 모두 실행 한다. (lambda 식으로 묶어줘야 함)
        assertAll(
                //실패시 에러메세지를 포함시킬 수 있다.
                //lambda 형식을쓴다면, 매번 문자열 연산이 있는경우 라도 필요한 경우에만 연산한다.즉 실패시에만 연산한다.
                () -> assertThat(study).isNotNull(),
                () -> assertThat(study.getLimit()).isGreaterThan(0),
                () -> assertThat(StudyStatus.DRAFT).as(() ->"스터디를 처음 만들면 상태값이 "+  StudyStatus.DRAFT +" 여야 한다.").isEqualTo(study.getStatus()),
                () -> assertThat(study.getLimit() > 0).as("스터디 최대 참가 인원은 0보다 커야 한다.").isTrue()
        );
    }

    @Test
    public void create_new_study_again() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertThat(message).isEqualTo("limit 은 0 보다 커야 합니다.");
    }

    @Test
    @DisplayName("스터디 타임아웃 테스트.")
    public void study_timeout() {
        //아래코드는 해당 블록이 끝날때까지 기다린다.
//        assertTimeout(Duration.ofMillis(100), () ->{
//            new Study(10);
//            Thread.sleep(150);
//        });

        //기준이된 시간이 지나면 실패로 간주한다.
        assertTimeoutPreemptively(Duration.ofMillis(100), () ->{
            new Study(10);
            Thread.sleep(50);
        });
    }

    @Test
    @Disabled
    public void create2() {
        System.out.println("사용하고 싶지 않은 테스트일 경우 @Disabled 를 붙인다.");
    }
}