package com.sinjwu.backend.service;

import com.sinjwu.backend.dto.CommentRequest;
import com.sinjwu.backend.dto.CommentResponse;
import com.sinjwu.backend.entity.Comment;
import com.sinjwu.backend.entity.Post;
import com.sinjwu.backend.entity.User;
import com.sinjwu.backend.exception.ResourceNotFoundException;
import com.sinjwu.backend.repository.CommentRepository;
import com.sinjwu.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;

    public CommentResponse createComment(Long postId, CommentRequest request) {
        User currentUser = authenticationService.getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(currentUser)
                .build();

        comment = commentRepository.save(comment);
        return CommentResponse.fromEntity(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long postId, Pageable pageable) {
        authenticationService.getCurrentUser();

        postRepository.findByIdAndNotDeleted(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found"));

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.map(CommentResponse::fromEntity);
    }
}
