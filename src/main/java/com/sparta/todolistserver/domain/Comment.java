package com.sparta.todolistserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public void updateContent(String content) {
        this.content = content;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
