package me.injin.the_java_second_test_code.study;


import me.injin.the_java_second_test_code.domain.Member;
import me.injin.the_java_second_test_code.domain.Study;
import me.injin.the_java_second_test_code.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //@Mock Annotation 을 사용하기위한 extension - 필수
class StudyServiceTestByMock {
    private final static Logger log = LogManager.getLogger(StudyServiceTestByMock.class);
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    //mock 객체 만들기
    @Test
    void createStudyService1() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertThat(studyService).isNotNull();
    }

    //mock 객체를 매개변수로 받기
    @Test
    void createStudyService2(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();
    }

    //mock 객체를 전역 Mock Annotation 을 이용해서 받기
    @Test
    void createStudyService3() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();
    }

    //mock Stubbing (mock 객체의 행동)
    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();

        Member member = new Member();
        member.setId(1L);
        member.setEmail("injin@email.com");

        //when - > 조건을 받아 mock 객체를 반환한다. Argument matchers 의 any() 를 사용하면 모든것을 허용한다.
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");
        Optional<Member> byId = memberService.findById(1L);
        assertThat("injin@email.com").isEqualTo(byId.get().getEmail());

//        when(memberService.findById(1L)).thenThrow(new RuntimeException());
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
           memberService.validate(1L);
        });
    }

    /**
     * 메소드가 동일한 매개변수로 여러번 호출될 때 각기 다르게 행동하도록 조작할 수 있다.
     */
    @Test
    void createNewStudy2() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertThat(studyService).isNotNull();

        Member member = new Member();
        member.setId(1L);
        member.setEmail("injin@email.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member)) //첫번째 호출 반환
                .thenThrow(new RuntimeException()) //두번째 호출 반환
                .thenReturn(Optional.empty()) //세번째 호출 반환
        ;

        //첫번째
        Optional<Member> byId = memberService.findById(3L);
        assertThat("injin@email.com").isEqualTo(byId.get().getEmail());
        //두번째
        assertThrows(RuntimeException.class, () ->{
           memberService.findById(1L);
        });
        //세번재
        assertThat(Optional.empty()).isEqualTo(memberService.findById(2L));
    }


}