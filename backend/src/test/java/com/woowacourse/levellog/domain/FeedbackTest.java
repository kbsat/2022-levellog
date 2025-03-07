package com.woowacourse.levellog.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.levellog.common.exception.InvalidFieldException;
import com.woowacourse.levellog.feedback.domain.Feedback;
import com.woowacourse.levellog.feedback.dto.FeedbackContentDto;
import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.member.domain.Member;
import com.woowacourse.levellog.team.domain.Team;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Feedback의")
class FeedbackTest {

    @Nested
    @DisplayName("생성자는")
    class create {

        @Test
        @DisplayName("Study 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void feedback_studyLength_exceptionThrown() {
            // given
            final String feedback = "f".repeat(1001);
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    feedback, "Speak 피드백", "Etc 피드백");

            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            // when & then
            assertThatThrownBy(() -> feedbackContentDto.toFeedback(roma, levellog))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }

        @Test
        @DisplayName("Speak 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void feedback_SpeakLength_exceptionThrown() {
            // given
            final String feedback = "f".repeat(1001);
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    "Study 피드백", feedback, "Etc 피드백");

            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            // when & then
            assertThatThrownBy(() -> feedbackContentDto.toFeedback(roma, levellog))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }

        @Test
        @DisplayName("Etc 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void feedback_EtcLength_exceptionThrown() {
            // given
            final String feedback = "f".repeat(1001);
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    "Study 피드백", "Speak 피드백", feedback);

            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            // when & then
            assertThatThrownBy(() -> feedbackContentDto.toFeedback(roma, levellog))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }
    }

    @Nested
    @DisplayName("updateFeedback 메소드는")
    class update {

        @Test
        @DisplayName("Study 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void update_studyLength_exceptionThrown() {
            // given
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    "Study 피드백", "Speak 피드백", "Etc 피드백");
            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            final Feedback feedback = feedbackContentDto.toFeedback(roma, levellog);

            final String studyFeedback = "f".repeat(1001);

            // when & then
            assertThatThrownBy(() -> feedback.updateFeedback(studyFeedback, "Speak 피드백", "Etc 피드백"))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }

        @Test
        @DisplayName("Speak 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void update_SpeakLength_exceptionThrown() {
            // given
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    "Study 피드백", "Speak 피드백", "Etc 피드백");
            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            final Feedback feedback = feedbackContentDto.toFeedback(roma, levellog);

            final String speakFeedback = "f".repeat(1001);

            // when & then
            assertThatThrownBy(() -> feedback.updateFeedback("Study 피드백", speakFeedback, "Etc 피드백"))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }

        @Test
        @DisplayName("Etc 피드백의 길이가 1000자 초과일 경우 예외를 발생시킨다.")
        void update_EtcLength_exceptionThrown() {
            // given
            final FeedbackContentDto feedbackContentDto = new FeedbackContentDto(
                    "Study 피드백", "Speak 피드백", "Etc 피드백");
            final Member roma = new Member("로마", 123456, "image.png");
            final Member eve = new Member("이브", 123123, "image.png");
            final Team team = new Team("잠실 네오조", "트랙룸", LocalDateTime.now().plusDays(3), "progile.img", 1);
            final Levellog levellog = Levellog.of(eve, team, "레벨로그 작성 내용");

            final Feedback feedback = feedbackContentDto.toFeedback(roma, levellog);

            final String etcFeedback = "f".repeat(1001);

            // when & then
            assertThatThrownBy(() -> feedback.updateFeedback("Study 피드백", "Speak 피드백", etcFeedback))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining("피드백은 1000자 이하여야 합니다.");
        }
    }
}
