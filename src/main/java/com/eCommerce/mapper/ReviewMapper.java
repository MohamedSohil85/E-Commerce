package com.eCommerce.mapper;

import com.eCommerce.dtos.reviews.ReviewsDto;
import com.eCommerce.entities.Reviews;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper implements EntityMapper<ReviewsDto, Reviews> {

    ModelMapper mapper=new ModelMapper();
    @Override
    public Reviews toEntity(ReviewsDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(dto,Reviews.class);
    }

    @Override
    public ReviewsDto toDto(Reviews entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,ReviewsDto.class);
    }

    @Override
    public List<Reviews> toEntity(List<ReviewsDto> dtoList) {
        return null;
    }

    @Override
    public List<ReviewsDto> toDto(List<Reviews> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<ReviewsDto>dtos=new ArrayList<>();
        entityList.forEach(reviews -> {
            dtos.add(mapper.map(reviews,ReviewsDto.class));
        });
        return dtos;
    }
}
