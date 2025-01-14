package org.rbnk.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.rbnk.api.dto.CommentDto;
import org.rbnk.api.entity.CommentEntity;
import org.rbnk.core.domain.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment entityToDomain(CommentEntity entity);

    CommentDto entityToDto(CommentEntity entity);

    CommentEntity domainToEntity(Comment domain);

    Comment dtoToDomain(CommentDto dto);

    CommentDto domainToDto(Comment domain);

    CommentEntity dtoToEntity(CommentDto dto);
}
