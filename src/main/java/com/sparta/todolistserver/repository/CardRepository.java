package com.sparta.todolistserver.repository;

import com.sparta.todolistserver.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByMember_Username(String username);

}
