package me.injin.the_java_second_test_code.member;


import me.injin.the_java_second_test_code.domain.Study;

import java.lang.reflect.Member;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);
}
