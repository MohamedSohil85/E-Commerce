package com.eCommerce.mapper;

import com.eCommerce.dtos.AdDto.CategoryDto;
import com.eCommerce.entities.Category;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper implements EntityMapper<CategoryDto, Category> {

    ModelMapper mapper=new ModelMapper();
    @Override
    public Category toEntity(CategoryDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(dto,Category.class);
    }

    @Override
    public CategoryDto toDto(Category entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return mapper.map(entity,CategoryDto.class);
    }

    @Override
    public List<Category> toEntity(List<CategoryDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Category>categories=new ArrayList<>();
        dtoList.forEach(categoryDto -> {
            categories.add(mapper.map(categoryDto,Category.class));
        });
        return categories;
    }

    @Override
    public List<CategoryDto> toDto(List<Category> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<CategoryDto>categoryDtos=new ArrayList<>();
        entityList.forEach(category -> {
            categoryDtos.add(mapper.map(category,CategoryDto.class));
        });
        return categoryDtos;
    }
}
