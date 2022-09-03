package me.injin.the_java_second_test_code;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

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
        Study study = new Study();
        Assertions.assertThat(study).isNotNull();
        System.out.println("create");
    }

    @Test
    public void create_new_study_again() {
        System.out.println("create1");
    }

    @Test
    @Disabled
    public void create2() {
        System.out.println("사용하고 싶지 않은 테스트일 경우 @Disabled 를 붙인다.");
    }
}