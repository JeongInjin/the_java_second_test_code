package me.injin.the_java_second_test_code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//@ExtendWith(FindSlowTestExtentsion.class) //해당 클래스를 생성자 없이 공통적용하려면.. - 선언적인 등록 방식
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)//displayName 전략 딱히 필요x 필요시 test > resources 로 설정 이관
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //필요시 test > resources 로 설정 이관
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //순서
class StudyTest {
    private final static Logger log = LogManager.getLogger(StudyTest.class);

    //아래는 @ExtendWith(FindSlowTestExtentsion.class) 를 생성자로 받아내기위한(커스텀하게 개별로) - 프로그래밍 등록 방식
    @RegisterExtension
    static FindSlowTestExtentsion findSlowTestExtentsion = new FindSlowTestExtentsion(500L);

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
     * Annotation 조건에 따른 설정으로 테스트를 추가, 제외 시킬 수 있다.
     * https://effortguy.tistory.com/121
     */
    @Test
    @DisplayName("환경 변수 조건에 따른 테스트.")
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "local")
    public void EnabledIfEnvironmentVariableLocal() {
        log.info("EnabledIfEnvironmentVariable: ENV: local");
    }

    @Test
    @DisplayName("Annotation Enabled OS 조건에 따른 테스트.")
//    @EnabledOnOs(OS.MAC)
    @EnabledOnOs({OS.MAC, OS.SOLARIS})
    public void EnabledOnOsMAC() {
        log.info("EnabledOnOs: OS.MAC 또는 OS.SOLARIS 입니다.");
    }

    @Test
    @DisplayName("Annotation Disabled OS 조건에 따른 테스트.")
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

    /**
     * 커스텀 태그
     * Annotation 을 생성하여, Tag Annotation 을 달지않고도 같은 효과를 가질 수 있다.
     */
    @FastTest
    @DisplayName("커스텀 태그를 통한 지정 테스트 - fast")
    void custom_tag_fast() {
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("커스텀 태그를 통한 지정 테스트 - fast")
    void custom_tag_slow() {
        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    /**
     * 반복 테스트 repetitionInfo 인자를 통해 현재 몇번째 반복인지 알 수 있다.
     * 반복시 마다 매개변수의 값을 변경하면서 테스트 하고 싶다면 ParameterizedTest 를 이용하면 된다.
     */
    @DisplayName("반복 테스트를 진행한다.")
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        log.info("반복 테스트 {}", repetitionInfo);
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"a", "b", "c", "d"})
    @DisplayName("매개변수 변경 반복 테스트")
    void parameterizedTest(String message) {
        log.info("message: {}", message);
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"a", "b", "c", "d"})
//    @EmptySource //인자에 빈값을 추가한다
//    @NullSource //인자에 널값을 추가한다
    @NullAndEmptySource //위 2가지 Annotation 을 합친경우
    @DisplayName("매개변수 변경 반복 테스트 2")
    void parameterizedTest2(String message) {
        log.info("message: {}", message);
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10, 20, 30, 40})
    @DisplayName("StudyConvert 를 이용한 테스트")
    void parameterizedTest3(@ConvertWith(StudyConvert.class) Study study ) {
        log.info("message: {}", study.getLimit());
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '닷넷 스터디'"})
    @DisplayName("CsvSource 를 이용한 매개변수 받기")
    void parameterizedTest4(Integer limit, String name) {
        Study study = new Study(limit, name);
        log.info("message: {}", study);
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '닷넷 스터디'"})
    @DisplayName("CsvSource 와 argumentsAccessor 를 이용한 객체 받아 파싱")
    void parameterizedTest5(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        log.info("message: {}", study);
    }

    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '닷넷 스터디'"})
    @DisplayName("CsvSource 와 argumentsAccessor 를 이용한 파싱된 객체 받기")
    void parameterizedTest6(@AggregateWith(StudyAggregator.class) Study study) {
        log.info("message: {}", study);
    }

    //매개변수 convert
    static class StudyConvert extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertThat(Study.class).as("Can only convert to String").isEqualTo(targetType);
            return new Study(Integer.parseInt(source.toString()));
        }
    }
    //객체로 변환
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    /**
     * 테스트 인스턴스
     * 테스트 클래스는 각 메서드를 생성시마다 새로운 인스턴스를 생성하기 때문에 전역변수를 사용해도 초기값만 나온다.(ex: int n = 1, n++, n++...)
     *      - 해당 방식으로 진행하는 이유는 테스트간의 의존성을 없애기 위해서임.
     * 전략변경을 원한다면 TestInstance Annotation 을 이용해야함.
     * -참고: beforeAll, afterAll 은 반드시 static class 여야하지만, TestInstance 의 옵션이 perClass 일 경우 static 이 아니여도 된다.
     */
    int number = 0;

    @Order(0)
    @Test
    @DisplayName("@TestInstance 의 작동 테스트1 - 순서도 추가")
    void TestInstance1() {
        log.info("number: {}", number++);
    }

    @Order(1)
    @Test
    @DisplayName("@TestInstance 의 작동 테스트2 - 순서도 추가")
    void TestInstance2() {
        log.info("number: {}", number++);
    }

    /**
     * 확장 모델
     * JUnit5 는 확장 모델은 단 하나 -> Extension
     * - 확장팩 등록 방법
     *      - 선언전인 등록 @ExtendWith
     *      - 프로그래밍 등록 @RegisterExtension
     *      - 자동 등록 자바 Serv
     */
    @Order(3)
    @Test
    @DisplayName("@ExtendWith(class Annotation) 을 이용한 선언적인 등록 => 느린 메서드 찾기")
    void fineSlowTest1() throws InterruptedException {
        Thread.sleep(500);
        log.info("fineSlowTest1");
    }

    @Order(2)
    @Test
    @SlowTest
    @DisplayName("@ExtendWith SlowTest Annotation 이 있는경우 권고 로그를 내지 않는다.")
    void fineSlowTest2() throws InterruptedException {
        Thread.sleep(500);
        log.info("fineSlowTest1");
    }
}