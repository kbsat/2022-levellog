package com.woowacourse.levellog.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GithubProfileDto {

    @JsonProperty("id")
    private String githubId;

    @JsonProperty("login")
    private String nickname;

    @JsonProperty("avatar_url")
    private String profileUrl;
}
