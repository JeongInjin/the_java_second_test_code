package me.injin.the_java_second_test_code.study;

import me.injin.the_java_second_test_code.domain.Study;
import me.injin.the_java_second_test_code.member.InvalidMemberException;
import me.injin.the_java_second_test_code.member.MemberService;

import java.lang.reflect.Member;
import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) throws InvalidMemberException {
        assert memberService != null;
        assert repository != null;

        Optional<Member> member = memberService.findById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
        }
        study.setOwnerId(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")));
        return repository.save(study);
    }
}
