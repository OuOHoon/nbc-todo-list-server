package com.sparta.todolistserver.repository;

import com.sparta.todolistserver.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
