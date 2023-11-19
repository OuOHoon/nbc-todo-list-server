package com.sparta.todolistserver.repository;

import com.sparta.todolistserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
