package com.eCommerce.mapper;

import com.eCommerce.dtos.accountdto.VendorDto;
import com.eCommerce.entities.Vendor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class VendorMapper implements EntityMapper<VendorDto, Vendor> {

    ModelMapper mapper=new ModelMapper();
    @Override
    public Vendor toEntity(VendorDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto,Vendor.class);
    }

    @Override
    public VendorDto toDto(Vendor entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,VendorDto.class);
    }

    @Override
    public List<Vendor> toEntity(List<VendorDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Vendor>vendors=new ArrayList<>();
        dtoList.forEach(vendorDto -> {
            vendors.add(mapper.map(vendorDto,Vendor.class));
        });
        return vendors;
    }

    @Override
    public List<VendorDto> toDto(List<Vendor> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<VendorDto>vendors=new ArrayList<>();
        entityList.forEach(vendor -> {
            vendors.add(mapper.map(vendor,VendorDto.class));
        });
        return vendors;
    }
}
