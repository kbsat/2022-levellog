package com.woowacourse.levellog.interviewquestion.dto;

import com.woowacourse.levellog.interviewquestion.domain.InterviewQuestion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewQuestionDetailDto {

    private Long id;
    private String interviewQuestion;

    public static InterviewQuestionDetailDto of(final InterviewQuestion interviewQuestion) {
        return new InterviewQuestionDetailDto(interviewQuestion.getId(), interviewQuestion.getContent());
    }
}
