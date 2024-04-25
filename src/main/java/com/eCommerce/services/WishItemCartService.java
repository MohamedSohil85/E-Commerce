package com.eCommerce.services;

import com.eCommerce.dtos.AdDto.WishDto;
import com.eCommerce.entities.Ad;
import com.eCommerce.entities.User;
import com.eCommerce.entities.WishItem;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.WishItemMapper;
import com.eCommerce.persistence.UserRepository;
import com.eCommerce.persistence.WishItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class WishItemCartService {
    @Inject
    WishItemRepository wishItemRepository;
    WishItemMapper wishItemMapper=new WishItemMapper();
    @Inject
    UserRepository userRepository;
    @Transactional
    public void addItemToWishList(String productName, Long userId) throws ResourceNotFoundException {
        Optional<Ad> ad= Ad.find("productName",productName).singleResultOptional();
        Ad ad_=ad.orElseThrow(()->new ResourceNotFoundException("Product with Name :"+productName+ " not found"));
        User user=User.findById(userId);
        WishItem wishItem=new WishItem();
        wishItem.setAd(ad_);
        wishItem.setUser(user);
        wishItem.setAddToWishCart(new Date());
        wishItem.persist();
        wishItemMapper.toDto(wishItem);
    }
    @Transactional
    public void removeItemFromListById(Long wishItemId){

     wishItemRepository.findByIdOptional(wishItemId).ifPresent(wishItem -> {
         wishItemRepository.delete(wishItem);
     });
    }
    public List<WishDto>loadItemsFromListByUserId(Long userId) throws ResourceNotFoundException {

     List<WishItem>wishItems=userRepository
             .findByIdOptional(userId)
             .map(User::getWishItems)
             .orElseThrow(()->new ResourceNotFoundException("List is empty !"));


        return wishItemMapper.toDto(wishItems);
    }
}
