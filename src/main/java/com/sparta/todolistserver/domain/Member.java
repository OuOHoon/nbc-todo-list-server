package com.sparta.todolistserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Card> cards;
}
