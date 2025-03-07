package com.woowacourse.levellog.levellog.dto;

import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.member.domain.Member;
import com.woowacourse.levellog.team.domain.Team;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevellogDto {

    @NotBlank
    private String content;

    public static LevellogDto from(final String content) {
        return new LevellogDto(content);
    }

    public static LevellogDto from(final Levellog levellog) {
        return new LevellogDto(levellog.getContent());
    }

    public Levellog toLevellog(final Member author, final Team team) {
        return Levellog.of(author, team, content);
    }
}
