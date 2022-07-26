package com.woowacourse.levellog.authentication.application;

import com.woowacourse.levellog.authentication.domain.OAuthClient;
import com.woowacourse.levellog.authentication.dto.GithubCodeRequest;
import com.woowacourse.levellog.authentication.dto.GithubProfileDto;
import com.woowacourse.levellog.authentication.dto.LoginResponse;
import com.woowacourse.levellog.authentication.support.JwtTokenProvider;
import com.woowacourse.levellog.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {

    private final MemberService memberService;
    private final OAuthClient oAuthClient;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(final GithubCodeRequest codeRequest) {
        final String code = codeRequest.getAuthorizationCode();
        final String githubAccessToken = oAuthClient.getAccessToken(code);

        final GithubProfileDto githubProfile = oAuthClient.getProfile(githubAccessToken);
        final Long memberId = getMemberIdByGithubProfile(githubProfile);

        final String token = jwtTokenProvider.createToken(memberId.toString());

        return new LoginResponse(memberId, token, githubProfile.getProfileUrl());
    }

    private Long getMemberIdByGithubProfile(final GithubProfileDto githubProfile) {
        final int githubId = Integer.parseInt(githubProfile.getGithubId());

        return memberService.saveIfNotExist(githubProfile, githubId);
    }
}
