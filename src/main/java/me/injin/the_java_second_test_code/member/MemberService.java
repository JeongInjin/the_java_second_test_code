package me.injin.the_java_second_test_code.member;


import me.injin.the_java_second_test_code.domain.Member;
import me.injin.the_java_second_test_code.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}