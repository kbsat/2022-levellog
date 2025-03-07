package com.woowacourse.levellog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.levellog.common.config.JpaConfig;
import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.levellog.domain.LevellogRepository;
import com.woowacourse.levellog.member.domain.Member;
import com.woowacourse.levellog.member.domain.MemberRepository;
import com.woowacourse.levellog.prequestion.domain.PreQuestion;
import com.woowacourse.levellog.prequestion.domain.PreQuestionRepository;
import com.woowacourse.levellog.team.domain.Team;
import com.woowacourse.levellog.team.domain.TeamRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
@DisplayName("PreQuestionRepository의")
class PreQuestionRepositoryTest {

    @Autowired
    PreQuestionRepository preQuestionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LevellogRepository levellogRepository;

    @Test
    @DisplayName("findByIdAndAuthor 메서드는 preQuestionId와 From 멤버가 같은 사전 질문을 반환한다.")
    void findByIdAndAuthor() {
        // given
        final Member author = memberRepository.save(new Member("알린", 12345678, "알린.img"));
        final Team team = teamRepository.save(new Team("선릉 네오조", "목성방", LocalDateTime.now().plusDays(3), "네오조.img", 1));
        final Levellog levellog = levellogRepository.save(Levellog.of(author, team, "알린의 레벨로그"));

        final Member questioner = memberRepository.save(new Member("로마", 56781234, "로마.img"));
        final String content = "로마가 쓴 사전 질문입니다.";
        final PreQuestion preQuestion = preQuestionRepository.save(new PreQuestion(levellog, questioner, content));

        // when
        final Optional<PreQuestion> actual = preQuestionRepository.findByIdAndAuthor(preQuestion.getId(), questioner);

        // then
        assertThat(actual).hasValue(preQuestion);
    }

    @Test
    @DisplayName("findByLevellogAndAuthor 메서드는 Levellog와 Author가 같은 사전 질문을 반환한다.")
    void findByLevellogAndAuthor() {
        // given
        final Member levellogAuthor = memberRepository.save(new Member("알린", 12345678, "알린.img"));
        final Team team = teamRepository.save(new Team("선릉 네오조", "목성방", LocalDateTime.now().plusDays(3), "네오조.img", 1));
        final Levellog levellog = levellogRepository.save(Levellog.of(levellogAuthor, team, "알린의 레벨로그"));

        final Member questioner = memberRepository.save(new Member("로마", 56781234, "로마.img"));
        final String content = "로마가 쓴 사전 질문입니다.";
        final PreQuestion preQuestion = preQuestionRepository.save(new PreQuestion(levellog, questioner, content));

        // when
        final Optional<PreQuestion> actual = preQuestionRepository.findByLevellogAndAuthor(levellog, questioner);

        // then
        assertThat(actual).hasValue(preQuestion);
    }

    @Test
    @DisplayName("findByLevellogAndAuthorId 메서드는 Levellog와 AuthorId가 같은 사전 질문을 반환한다.")
    void findByLevellogAndAuthorId() {
        // given
        final Member levellogAuthor = memberRepository.save(new Member("알린", 12345678, "알린.img"));
        final Team team = teamRepository.save(new Team("선릉 네오조", "목성방", LocalDateTime.now().plusDays(3), "네오조.img", 1));
        final Levellog levellog = levellogRepository.save(Levellog.of(levellogAuthor, team, "알린의 레벨로그"));

        final Member questioner = memberRepository.save(new Member("로마", 56781234, "로마.img"));
        final String content = "로마가 쓴 사전 질문입니다.";
        final PreQuestion preQuestion = preQuestionRepository.save(new PreQuestion(levellog, questioner, content));

        // when
        final Optional<PreQuestion> actual = preQuestionRepository.findByLevellogAndAuthorId(levellog, questioner.getId());

        // then
        assertThat(actual).hasValue(preQuestion);
    }

    @Nested
    @DisplayName("existsByLevellogAndAuthor 메서드는")
    class ExistsByLevellogAndAuthorTest {

        @Test
        @DisplayName("levellog와 사전 질문의 author가 모두 일치하는 사전 질문이 존재하는 경우 true를 반환한다.")
        void exists() {
            // given
            final Member levellogAuthor = memberRepository.save(new Member("알린", 12345678, "알린.img"));
            final Team team = teamRepository.save(new Team("선릉 네오조", "목성방", LocalDateTime.now().plusDays(3), "네오조.img", 1));
            final Levellog levellog = levellogRepository.save(Levellog.of(levellogAuthor, team, "알린의 레벨로그"));

            final Member questioner = memberRepository.save(new Member("로마", 56781234, "로마.img"));
            final String content = "로마가 쓴 사전 질문입니다.";
            preQuestionRepository.save(new PreQuestion(levellog, questioner, content));

            // when
            final boolean actual = preQuestionRepository.existsByLevellogAndAuthor(levellog, questioner);

            // then
            assertTrue(actual);
        }

        @Test
        @DisplayName("levellog와 사전 질문의 author가 모두 일치하는 사전 질문이 존재하지 않는 경우 false를 반환한다.")
        void notExists() {
            // given
            final Member levellogAuthor = memberRepository.save(new Member("알린", 12345678, "알린.img"));
            final Team team = teamRepository.save(new Team("선릉 네오조", "목성방", LocalDateTime.now().plusDays(3), "네오조.img", 1));
            final Levellog levellog = levellogRepository.save(Levellog.of(levellogAuthor, team, "알린의 레벨로그"));
            final Member questioner = memberRepository.save(new Member("로마", 56781234, "로마.img"));

            // when
            final boolean actual = preQuestionRepository.existsByLevellogAndAuthor(levellog, questioner);

            // then
            assertFalse(actual);
        }
    }
}
