package com.eCommerce.mapper;

import com.eCommerce.dtos.AdDto.AdDto;
import com.eCommerce.entities.Ad;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class AdMapper implements EntityMapper<AdDto,Ad> {

    ModelMapper mapper=new ModelMapper();
    @Override
    public Ad toEntity(AdDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto,Ad.class);
    }

    @Override
    public AdDto toDto(Ad entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,AdDto.class);
    }

    @Override
    public List<Ad> toEntity(List<AdDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Ad>ads=new ArrayList<>();
        dtoList.forEach(adDto -> {
            ads.add(mapper.map(adDto,Ad.class));
        });
        return ads;
    }

    @Override
    public List<AdDto> toDto(List<Ad> entityList) {
        return null;
    }
}
