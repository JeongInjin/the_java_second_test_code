package me.injin.the_java_second_test_code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {
    private final static Logger log = LogManager.getLogger(StudyTest.class);

//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("static 메서드여야하고 반환값이 없어야함, 테스트 시작 전 1번만 실행 beforeAll");
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        System.out.println("static 메서드여야하고 반환값이 없어야함, 테스트 시작 후 1번만 실행 AfterAll");
//    }
//
//    @BeforeEach
//    public void BeforeEach() {
//        System.out.println("테스트 시작 전 실행 -> BeforeEach");
//    }
//
//    @AfterEach
//    public void AfterEach() {
//        System.out.println("테스트 종료 후 실행 -> AfterEach");
//    }

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
    @DisplayName("코드 조건에 따른 테스트.")
    public void study_condition_test() {
        String test_value = "ok";
        assumeTrue("ok".equalsIgnoreCase(test_value)); //해당 조건이 true 인경우 진행한다.

        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
        log.info("조건 true 로 인하여 테스트 완료.");

        /**
         * assumingThat(boolean, executable)
         * 첫 번째 인자가 True면 두 번째 인자로 들어온 함수 실행
         * 첫 번째 인자 값이 false 인 경우에도 테스트를 스킵하지 않고 다음 코드를 진행합니다.
         */
        assumingThat("not_ok".equalsIgnoreCase(test_value), () -> {
            assertThat(study.getLimit()).isGreaterThan(0);
            log.info("assumingThat false");
        });

        assumingThat("ok".equalsIgnoreCase(test_value), () -> {
            assertThat(study.getLimit()).isGreaterThan(0);
            log.info("assumingThat true");
        });

    }

    /**
     * Annotaion 조건에 따른 설정으로 테스트를 추가, 제외 시킬 수 있다.
     * https://effortguy.tistory.com/121
     */
    @Test
    @DisplayName("환경 변수 조건에 따른 테스트.")
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "local")
    public void EnabledIfEnvironmentVariableLocal() {
        log.info("EnabledIfEnvironmentVariable: ENV: local");
    }

    @Test
    @DisplayName("Annotaion Enabled OS 조건에 따른 테스트.")
//    @EnabledOnOs(OS.MAC)
    @EnabledOnOs({OS.MAC, OS.SOLARIS})
    public void EnabledOnOsMAC() {
        log.info("EnabledOnOs: OS.MAC 또는 OS.SOLARIS 입니다.");
    }

    @Test
    @DisplayName("Annotaion Disabled OS 조건에 따른 테스트.")
    @DisabledOnOs({OS.WINDOWS, OS.LINUX})
    public void EnabledOnOsWINDOWS() {
        log.info("DisabledOnOs: OS.WINDOWS, OS.LINUX 가 아닙니다.");
    }


    @Test
    @Disabled
    public void create2() {
        System.out.println("사용하고 싶지 않은 테스트일 경우 @Disabled 를 붙인다.");
    }


    /**
     * 태깅과 필터링
     * 에너테이션을 사용하여 태스트를 그룹화 시킬 수 있다.
     * 하나의 테스트 메소드에 여러 태그도 사용 가능하다.
     * Edit Configurations.. 에서 class 설정이 아닌 tags 로 변경후 tag 값을 넣어주면 된다.
     * 터미널에서 명령어:  ./gradlew test  로 하게되면 test 가 전체 수행되지만, maven, gradle 로 profile 환경을 나누면 띠로 실행도 가능하다.
     */
    @Test
    @DisplayName("태그를 통한 지정 테스트 - fast")
    @Tag("fast")
    void taggingTest_tag_fast() {
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("태그를 통한 지정 테스트 - slow")
    @Tag("slow")
    void taggingTest_tag_slow() {
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

}