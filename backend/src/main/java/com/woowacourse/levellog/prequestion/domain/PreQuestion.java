package com.woowacourse.levellog.prequestion.domain;

import com.woowacourse.levellog.common.domain.BaseEntity;
import com.woowacourse.levellog.common.exception.InvalidFieldException;
import com.woowacourse.levellog.common.exception.UnauthorizedException;
import com.woowacourse.levellog.levellog.domain.Levellog;
import com.woowacourse.levellog.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PreQuestion extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_pre_question_levellog"))
    private Levellog levellog;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_pre_question_from_member"))
    private Member author;

    @Column(nullable = false)
    @Lob
    private String content;

    public PreQuestion(final Levellog levellog, final Member author, final String content) {
        validateSelfPreQuestion(levellog, author);
        validateContent(content);

        this.levellog = levellog;
        this.author = author;
        this.content = content;
    }

    private void validateSelfPreQuestion(final Levellog levellog, final Member member) {
        levellog.validateSelfPreQuestion(member);
    }

    private void validateContent(final String content) {
        if (content == null || content.isBlank()) {
            throw new InvalidFieldException("사전 내용은 공백이나 null일 수 없습니다.");
        }
    }

    public void update(final String content) {
        validateContent(content);

        this.content = content;
    }

    public boolean isSameLevellog(final Levellog levellog) {
        return this.levellog.equals(levellog);
    }

    public boolean isSameAuthor(final Member member) {
        return author.equals(member);
    }

    public void validateLevellog(final Levellog levellog) {
        if (!isSameLevellog(levellog)) {
            throw new InvalidFieldException(
                    "입력한 levellogId와 사전 질문의 levellogId가 다릅니다. 입력한 levellogId : " + levellog.getId());
        }
    }

    public void validateMyQuestion(final Member member) {
        if (!isSameAuthor(member)) {
            throw new UnauthorizedException(
                    "자신의 사전 질문이 아닙니다. 사전 질문 id = " + this.getId() + ", 멤버 id = " + member.getId());
        }
    }
}
