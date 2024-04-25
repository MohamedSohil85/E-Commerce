package com.eCommerce.mapper;

import com.eCommerce.dtos.AdDto.WishDto;
import com.eCommerce.entities.WishItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class WishItemMapper implements EntityMapper<WishDto, WishItem>{

    ModelMapper mapper=new ModelMapper();
    @Override
    public WishItem toEntity(WishDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(dto,WishItem.class);
    }

    @Override
    public WishDto toDto(WishItem entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,WishDto.class);
    }

    @Override
    public List<WishItem> toEntity(List<WishDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<WishItem>wishItems=new ArrayList<>();
        dtoList.forEach(wishDto -> {
            wishItems.add(mapper.map(wishDto,WishItem.class));
        });
        return wishItems;
    }

    @Override
    public List<WishDto> toDto(List<WishItem> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<WishDto>wishlists=new ArrayList<>();
        entityList.forEach(wishItem -> {
          wishlists.add(mapper.map(wishItem,WishDto.class));
        });
        return wishlists;
    }
}
