package org.rbnk.api.mapper;

import org.mapstruct.Mapper;
import org.rbnk.api.dto.NewsDto;
import org.rbnk.api.entity.NewsEntity;
import org.rbnk.core.domain.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News entityToDomain(NewsEntity entity);

    NewsDto entityToDto(NewsEntity entity);

    NewsEntity domainToEntity(News domain);

    News dtoToDomain(NewsDto dto);

    NewsDto domainToDto(News domain);

    NewsEntity dtoToEntity(NewsDto dto);
}
