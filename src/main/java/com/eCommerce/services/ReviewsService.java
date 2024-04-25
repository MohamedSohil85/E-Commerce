package com.eCommerce.services;

import com.eCommerce.dtos.reviews.ReviewsDto;
import com.eCommerce.entities.Ad;
import com.eCommerce.entities.AverageRating;
import com.eCommerce.entities.Reviews;
import com.eCommerce.entities.User;
import com.eCommerce.enums.Rating;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.ReviewMapper;
import com.eCommerce.persistence.AdRepository;
import com.eCommerce.persistence.ReviewsRepository;
import com.eCommerce.persistence.UserRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class ReviewsService {

    @Inject
    ReviewsRepository reviewsRepository;
    @Inject
    AdRepository adRepository;
    @Inject
    UserRepository userRepository;
    ReviewMapper reviewMapper=new ReviewMapper();


    public ReviewsDto addComment(String token, String productName, ReviewsDto reviews)throws ResourceNotFoundException{
       Optional<User>userOptional= userRepository.findUserByToken(token);
       User user=userOptional.orElseThrow(()->new ResourceNotFoundException("Resource not found"));
       Reviews reviews_=reviewMapper.toEntity(reviews);
       reviews_.setUser(user);
       reviews.setDateOfReview(new Date());
       Ad ad=adRepository.find("productName",productName).firstResultOptional().orElseThrow(()->new ResourceNotFoundException("Product with Name :"+productName+" not found !"));
        AverageRating averageRating=new AverageRating();
          int counter=0;
          int sum;
       switch (reviews.getRating()) {
           case 1 ->{
               averageRating.setRating(Rating.rating_1);
               counter++;
               averageRating.setNoOfRating(counter);
               averageRating.setTotalNumberOfRating(averageRating.getNoOfRating());
           }

           case 2 -> {
               averageRating.setRating(Rating.rating_2);
               counter++;
               averageRating.setNoOfRating(counter);
               averageRating.setTotalNumberOfRating(2*averageRating.getNoOfRating());
           }
           case 3 ->{
               averageRating.setRating(Rating.rating_3);
               counter++;
               averageRating.setNoOfRating(counter);
               averageRating.setTotalNumberOfRating(3*averageRating.getNoOfRating());

           }
           case 4 ->{
               averageRating.setRating(Rating.rating_4);
               counter++;
               averageRating.setNoOfRating(counter);
               averageRating.setTotalNumberOfRating(4*averageRating.getNoOfRating());

           }
           case 5 ->{
               averageRating.setRating(Rating.rating_5);
               counter++;
               averageRating.setNoOfRating(counter);
               averageRating.setTotalNumberOfRating(5*averageRating.getNoOfRating());
           }


        }
         sum=adRepository.find("productName",productName).stream().map(Ad::getAverageRating).mapToInt(AverageRating::getTotalNumberOfRating).sum();
       ad.setNoOfRating(sum);
       ad.setProductTotalReviews(averageRating.getTotalNumberOfRating()/sum);
        averageRating.persist();
       ad.persist();
       ad.getReviewsList().add(reviews_);
       reviews_.setAd(ad);
       reviews_.persist();
       return reviews;
    }
  public void removeComment(Long adId,Long reviewId){

        Reviews reviews=Reviews.findById(reviewId);
        reviews.setRating(0);
        Ad ad=Ad.findById(adId);
        Ad ad1=new Ad();
        ad1.setNoOfRating(ad.getNoOfRating()-1);
        reviews.delete();
  }
}
