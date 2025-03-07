package com.woowacourse.levellog.feedback.application;

import com.woowacourse.levellog.common.domain.BaseEntity;
import com.woowacourse.levellog.common.exception.UnauthorizedException;
import com.woowacourse.levellog.feedback.domain.Feedback;
import com.woowacourse.levellog.feedback.domain.FeedbackRepository;
import com.woowacourse.levellog.feedback.dto.FeedbackContentDto;
import com.woowacourse.levellog.feedback.dto.FeedbackDto;
import com.woowacourse.levellog.feedback.dto.FeedbackWriteDto;
import com.woowacourse.levellog.feedback.dto.FeedbacksDto;
import com.woowacourse.levellog.feedback.exception.FeedbackAlreadyExistException;
import com.woowacourse.levellog.feedback.exception.FeedbackNotFoundException;
import com.woowacourse.levellog.feedback.exception.InvalidFeedbackException;
import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.levellog.domain.LevellogRepository;
import com.woowacourse.levellog.levellog.exception.LevellogNotFoundException;
import com.woowacourse.levellog.member.domain.Member;
import com.woowacourse.levellog.member.domain.MemberRepository;
import com.woowacourse.levellog.member.exception.MemberNotFoundException;
import com.woowacourse.levellog.team.domain.Participant;
import com.woowacourse.levellog.team.domain.ParticipantRepository;
import com.woowacourse.levellog.team.domain.Team;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final LevellogRepository levellogRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public Long save(final FeedbackWriteDto request, final Long levellogId, final Long fromMemberId) {
        validateExistence(levellogId, fromMemberId);

        final Member member = getMember(fromMemberId);
        final FeedbackContentDto feedbackContent = request.getFeedback();
        final Levellog levellog = getLevellog(levellogId);

        levellog.validateSelfFeedback(member);
        validateTeamMember(levellogId, member);

        final Feedback feedback = feedbackContent.toFeedback(member, levellog);

        return feedbackRepository.save(feedback)
                .getId();
    }

    public FeedbacksDto findAll(final Long levellogId, final Long memberId) {
        final Levellog levellog = getLevellog(levellogId);
        final List<FeedbackDto> responses = getFeedbackResponses(feedbackRepository.findAllByLevellog(levellog));

        final boolean isParticipant = participantRepository.findByTeam(levellog.getTeam())
                .stream()
                .map(Participant::getMember)
                .map(BaseEntity::getId)
                .anyMatch(it -> it.equals(memberId));

        if (!isParticipant) {
            throw new UnauthorizedException("자신이 속한 팀의 피드백만 조회할 수 있습니다."
                    + " [ teamId : " + levellog.getTeam().getId() + " memberId : " + memberId
                    + " levellogId : " + levellogId + " ]");
        }

        return new FeedbacksDto(responses);
    }

    public FeedbacksDto findAllByTo(final Long memberId) {
        final Member member = getMember(memberId);
        final List<Feedback> feedbacks = feedbackRepository.findAllByToOrderByUpdatedAtDesc(member);

        return new FeedbacksDto(getFeedbackResponses(feedbacks));
    }

    @Transactional
    public void update(final FeedbackWriteDto request, final Long feedbackId, final Long memberId) {
        final Feedback feedback = getFeedback(feedbackId);
        final Member member = getMember(memberId);

        feedback.validateAuthor(member, "자신이 남긴 피드백만 수정할 수 있습니다.");

        feedback.updateFeedback(
                request.getFeedback().getStudy(),
                request.getFeedback().getSpeak(),
                request.getFeedback().getEtc());
    }

    @Transactional
    public void deleteById(final Long feedbackId, final Long memberId) {
        final Feedback feedback = getFeedback(feedbackId);
        final Member member = getMember(memberId);

        feedback.validateAuthor(member, "자신이 남긴 피드백만 삭제할 수 있습니다.");

        feedbackRepository.deleteById(feedbackId);
    }

    private void validateExistence(final Long levellogId, final Long fromMemberId) {
        if (feedbackRepository.existsByLevellogIdAndFromId(levellogId, fromMemberId)) {
            throw new FeedbackAlreadyExistException("피드백이 이미 존재합니다. levellogId : " + levellogId);
        }
    }

    private void validateTeamMember(final Long levellogId, final Member member) {
        final Team team = getLevellog(levellogId).getTeam();

        if (!participantRepository.existsByMemberAndTeam(member, team)) {
            throw new InvalidFeedbackException(
                    " [memberId :" + member.getId() + " teamId : " + team.getId() + "]",
                    "같은 팀에 속한 멤버만 피드백을 작성할 수 있습니다.");
        }
    }

    private List<FeedbackDto> getFeedbackResponses(final List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(FeedbackDto::from)
                .collect(Collectors.toList());
    }

    private Member getMember(final Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버가 존재하지 않음 [memberId : " + memberId + "]"));
    }

    private Levellog getLevellog(final Long levellogId) {
        return levellogRepository.findById(levellogId)
                .orElseThrow(() -> new LevellogNotFoundException("존재하지 않는 레벨로그. levellogId : " + levellogId));
    }

    private Feedback getFeedback(final Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException("존재하지 않는 피드백. feedbackId : " + feedbackId));
    }
}
