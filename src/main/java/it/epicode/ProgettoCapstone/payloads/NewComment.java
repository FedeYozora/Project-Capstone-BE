package it.epicode.ProgettoCapstone.payloads;

import it.epicode.ProgettoCapstone.enums.CommentStatus;

public record NewComment(
        String content,
        CommentStatus commentStatus

) {
}
