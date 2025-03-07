package com.woowacourse.levellog.member.presentation;

import com.woowacourse.levellog.authentication.support.Authentic;
import com.woowacourse.levellog.feedback.application.FeedbackService;
import com.woowacourse.levellog.feedback.dto.FeedbacksDto;
import com.woowacourse.levellog.levellog.application.LevellogService;
import com.woowacourse.levellog.levellog.dto.LevellogsDto;
import com.woowacourse.levellog.member.application.MemberService;
import com.woowacourse.levellog.member.dto.MemberDto;
import com.woowacourse.levellog.member.dto.NicknameUpdateDto;
import com.woowacourse.levellog.team.application.TeamService;
import com.woowacourse.levellog.team.dto.TeamsDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-info")
@RequiredArgsConstructor
public class MyInfoController {

    private final FeedbackService feedbackService;
    private final LevellogService levellogService;
    private final TeamService teamService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberDto> myInfo(@Authentic final Long memberId) {
        final MemberDto memberDto = memberService.findMemberById(memberId);

        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<FeedbacksDto> findAllFeedbackToMe(@Authentic final Long memberId) {
        final FeedbacksDto feedbacksResponse = feedbackService.findAllByTo(memberId);

        return ResponseEntity.ok(feedbacksResponse);
    }

    @GetMapping("/levellogs")
    public ResponseEntity<LevellogsDto> findAllMyLevellogs(@Authentic final Long memberId) {
        final LevellogsDto levellogsResponse = levellogService.findAllByAuthorId(memberId);

        return ResponseEntity.ok(levellogsResponse);
    }

    @GetMapping("/teams")
    public ResponseEntity<TeamsDto> findAllMyTeams(@Authentic final Long memberId) {
        final TeamsDto teamsDto = teamService.findAllByMemberId(memberId);

        return ResponseEntity.ok(teamsDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateNickname(@Authentic final Long memberId,
                                               @RequestBody @Valid final NicknameUpdateDto request) {
        memberService.updateNickname(request, memberId);

        return ResponseEntity.noContent().build();
    }
}
