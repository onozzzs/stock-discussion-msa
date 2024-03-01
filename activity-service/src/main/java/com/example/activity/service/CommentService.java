package com.example.activity.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.activity.api.UserAPI;
import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.dto.CommentActivityDTO;
import com.example.activity.dto.CommentRequestDTO;
import com.example.activity.dto.UserDTO;
import com.example.activity.dto.comment.CommentResponseDTO;
import com.example.activity.dto.post.PostResponseDTO;
import com.example.activity.model.Category;
import com.example.activity.model.Comment;
import com.example.activity.repository.CommentRepository;
import com.example.activity.model.Post;
import com.example.activity.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserAPI userAPI;

    @Autowired
    private TokenProvider tokenProvider;

    public PostResponseDTO getBoard(HttpServletRequest request, final Long postId) {
        String userId = tokenProvider.getUserId(request);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
        List<CommentResponseDTO> commentResponseDTOs = commentRepository.findByPostId(postId);

        PostResponseDTO postResponseDTO = new PostResponseDTO(post);
        postResponseDTO.setCommentResponseDTOList(commentResponseDTOs);

        return postResponseDTO;
    }

    public void saveComment(HttpServletRequest request, final Long postId, CommentRequestDTO commentRequestDTO) {
        String userId = tokenProvider.getUserId(request);
        UserDTO userDTO = userAPI.getUserByUserId(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));

        Comment comment = CommentRequestDTO.toEntity(commentRequestDTO);

        Comment parentComment;
        if (commentRequestDTO.getParentId() != null) {
            parentComment = commentRepository.findById(commentRequestDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException("Could not found comment id : " + commentRequestDTO.getParentId()));
            comment.updateParent(parentComment);
        }

        comment.updateUserId(userId);
        comment.updatePost(post);
        comment.updateIsDeleted(null);
        commentRepository.save(comment);

//        String content = Category.COMMENT.makeContent(userDTO.getUsername(), post.getTitle(), comment.getContent());
//        CommentActivityDTO commentActivityDTO = CommentActivityDTO.builder()
//                .userId(userId)
//                .commentId(comment.getId())
//                .content(content)
//                .category(Category.COMMENT)
//                .build();
//
//        activityService.saveCommentActivity(commentActivityDTO);
    }
}
