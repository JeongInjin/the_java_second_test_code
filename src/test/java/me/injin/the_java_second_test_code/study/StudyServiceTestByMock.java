package me.injin.the_java_second_test_code.study;

import me.injin.the_java_second_test_code.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //@Mock Annotation 을 사용하기위한 extension - 필수
class StudyServiceTestByMock {

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


}