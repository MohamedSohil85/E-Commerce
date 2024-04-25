package com.eCommerce.mapper;

import com.eCommerce.dtos.AdDto.Ads;
import com.eCommerce.entities.Ad;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class AdsMapper implements EntityMapper<Ads, Ad>{
    ModelMapper mapper=new ModelMapper();
    @Override
    public Ad toEntity(Ads dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(dto,Ad.class);
    }

    @Override
    public Ads toDto(Ad entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return mapper.map(entity,Ads.class);
    }

    @Override
    public List<Ad> toEntity(List<Ads> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Ad>ads=new ArrayList<>();
        dtoList.forEach(ads1 -> {
            ads.add(mapper.map(ads1,Ad.class));
        });
        return ads;
    }

    @Override
    public List<Ads> toDto(List<Ad> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Ads>ads=new ArrayList<>();
        entityList.forEach(ad -> {
            ads.add(mapper.map(ad,Ads.class));
        });

        return ads;
    }
}
