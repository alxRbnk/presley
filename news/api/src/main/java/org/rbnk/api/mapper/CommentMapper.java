package org.rbnk.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.rbnk.api.dto.CommentDto;
import org.rbnk.core.domain.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment dtoToDomain(CommentDto dto);

    CommentDto domainToDto(Comment domain);
}
