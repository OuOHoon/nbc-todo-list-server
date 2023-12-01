package com.sparta.todolistserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Card extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @Builder.Default
    private Boolean isFinished = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateCard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFinish() {
        this.isFinished = Boolean.TRUE;
    }
}
