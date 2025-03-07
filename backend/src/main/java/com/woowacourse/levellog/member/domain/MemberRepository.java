package com.woowacourse.levellog.member.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByGithubId(int githubId);

    List<Member> findAllByNicknameContains(String nickname);

    boolean existsByGithubId(int githubId);
}
