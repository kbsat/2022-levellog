package com.woowacourse.levellog.member.presentation;

import com.woowacourse.levellog.member.application.MemberService;
import com.woowacourse.levellog.member.dto.MembersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MembersDto> searchBy(@RequestParam(defaultValue = "") final String nickname) {
        final MembersDto response = memberService.searchByNickname(nickname);
        return ResponseEntity.ok(response);
    }
}
