package com.woowacourse.levellog.authentication.domain;

import com.woowacourse.levellog.authentication.dto.GithubProfileDto;

public interface OAuthClient {

    String getAccessToken(String authorizationCode);

    GithubProfileDto getProfile(String accessToken);
}
