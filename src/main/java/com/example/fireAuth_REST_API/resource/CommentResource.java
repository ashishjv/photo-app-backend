package com.example.fireAuth_REST_API.resource;

import com.example.fireAuth_REST_API.exception.InvalidTokenException;
import com.example.fireAuth_REST_API.exception.UserNotAuthorizedException;
import com.example.fireAuth_REST_API.model.Comment;
import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.service.CommentService;
import com.example.fireAuth_REST_API.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/photos/comment/{commentId}")
    public Comment getCommentById(@PathVariable(name = "commentId") String commentId,
                                  @RequestHeader(name = "idToken") String idToken) {
        return commentService.getCommentById(commentId);
    }

    @GetMapping("/photo/{photoId}/comments")
    public List<Comment> getAllCommentsByPhotoId(@PathVariable("photoId") String photoId,
                                                 @RequestHeader(name="idToken") String idToken)
            throws IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            return commentService.getAllCommentsByPhotoId(photoId);
        }
        else {
            throw new InvalidTokenException();
        }
    }

    @PostMapping("/photos/comments")
    public Comment saveComment(@RequestBody @Valid Comment comment,
                               @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            comment.setCreatedBy(firebaseUser.getEmail());
            if (comment.getDateCreated() == null) {
                comment.setDateCreated(java.time.LocalDate.now());
            }
            return commentService.saveComment(comment);
        } else {
            throw new InvalidTokenException();
        }
    }

    @PutMapping("/photos/comment/{commentId}")
    public Comment updateComment(@RequestBody @Valid Comment comment,
                                 @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(comment.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Comment.createdBy: " + comment.getCreatedBy()
                );
            } else {
                return commentService.updateComment(comment);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @DeleteMapping("/photos/comment/{commentId}")
    public String deleteComment(@RequestParam(name = "commentId") String commentId,
                                @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        Comment comment = commentService.getCommentById(commentId);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(comment.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Comment.createdBy: " + comment.getCreatedBy()
                );
            } else {
                return commentService.deleteComment(commentId);
            }
        } else {
            throw new InvalidTokenException();
        }
    }


}
