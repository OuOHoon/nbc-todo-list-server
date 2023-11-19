package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.config.auth.UserDetailsImpl;
import com.sparta.todolistserver.request.comment.CommentCreateRequest;
import com.sparta.todolistserver.request.comment.CommentUpdateRequest;
import com.sparta.todolistserver.response.BaseResponse;
import com.sparta.todolistserver.response.comment.CommentResponse;
import com.sparta.todolistserver.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards/{cardId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long cardId,
                                                         @RequestBody CommentCreateRequest request,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponse response = commentService.create(userDetails.getUsername(), cardId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long cardId,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentUpdateRequest request,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponse response = commentService.update(userDetails.getUsername(), commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse> removeComment(@PathVariable Long cardId,
                                                      @PathVariable Long commentId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.remove(userDetails.getUsername(), commentId);
        return new ResponseEntity<>(BaseResponse.of("remove comment", HttpStatus.NO_CONTENT.value()),
                HttpStatus.NO_CONTENT);
    }
}
