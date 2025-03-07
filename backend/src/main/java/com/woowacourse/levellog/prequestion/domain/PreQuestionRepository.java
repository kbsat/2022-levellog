package com.woowacourse.levellog.prequestion.domain;

import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreQuestionRepository extends JpaRepository<PreQuestion, Long> {

    Optional<PreQuestion> findByIdAndAuthor(Long id, Member author);

    Optional<PreQuestion> findByLevellogAndAuthor(Levellog levellog, Member author);

    Optional<PreQuestion> findByLevellogAndAuthorId(Levellog levellog, Long memberId);

    boolean existsByLevellogAndAuthor(Levellog levellog, Member author);
}
