package com.sinjwu.backend.service;

import com.sinjwu.backend.entity.Like;
import com.sinjwu.backend.entity.Post;
import com.sinjwu.backend.entity.User;
import com.sinjwu.backend.exception.BadRequestException;
import com.sinjwu.backend.repository.LikeRepository;
import com.sinjwu.backend.repository.PostRepository;
import com.sinjwu.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public boolean toggleLike(Long postId) {
        User currentUser = authenticationService.getCurrentUser();

        Post post = postRepository.findByIdAndNotDeleted(postId)
                .orElseThrow(() -> new BadRequestException("Post not found"));

        boolean alreadyLiked = likeRepository.existsByUserAndPost(currentUser, post);

        if (alreadyLiked) {
            likeRepository.deleteByUserAndPost(currentUser, post);
            return false;
        } else {
            Like like = Like.builder()
                    .user(currentUser)
                    .post(post)
                    .build();
            likeRepository.save(like);
            return false;
        }
    }
}
