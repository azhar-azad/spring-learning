package com.azad.CampusConnectApi.models.dtos;

import com.azad.CampusConnectApi.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto extends Comment {

    private Long id;
    private Long postId;
    private Long commentedToUserId;
}
