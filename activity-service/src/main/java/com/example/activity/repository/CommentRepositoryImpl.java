package com.example.activity.repository;

import com.example.activity.dto.comment.CommentResponseDTO;
import com.example.activity.model.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.activity.dto.comment.CommentResponseDTO.convertCommentToDto;
import static com.example.activity.model.QComment.comment;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponseDTO> findByPostId(Long id) {

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.post.id.eq(id))
                .orderBy(comment.parent.commentId.asc().nullsFirst(),
                        comment.createdAt.asc())
                .fetch();

        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        Map<Long, CommentResponseDTO> commentDTOHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentResponseDTO commentResponseDTO = convertCommentToDto(c);
            commentDTOHashMap.put(commentResponseDTO.getId(), commentResponseDTO);
            if (c.getParent() != null) commentDTOHashMap.get(c.getParent().getCommentId()).getChildren().add(commentResponseDTO);
            else commentResponseDTOList.add(commentResponseDTO);
        });
        return commentResponseDTOList;
    }

    @Override
    public Optional<Comment> findCommentByIdWithParent(Long id) {

        Comment selectedComment = queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.commentId.eq(id))
                .fetchOne();

        return Optional.ofNullable(selectedComment);
    }
}