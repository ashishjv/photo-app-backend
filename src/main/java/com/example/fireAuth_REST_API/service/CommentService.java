package com.example.fireAuth_REST_API.service;

import com.example.fireAuth_REST_API.model.Comment;
import com.example.fireAuth_REST_API.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).get();
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public String deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
        return "Deletion completed";
    }

    public List<Comment> getAllCommentsByPhotoId(String photoId){
        return commentRepository.findByPhotoId(photoId);
    }
}
