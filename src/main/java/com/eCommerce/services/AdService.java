package com.eCommerce.services;

import com.eCommerce.dtos.AdDto.AdDto;
import com.eCommerce.dtos.AdDto.Ads;
import com.eCommerce.entities.Ad;
import com.eCommerce.entities.SubCategory;
import com.eCommerce.entities.Vendor;
import com.eCommerce.enums.ItemCondition;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.AdMapper;
import com.eCommerce.mapper.AdsMapper;
import com.eCommerce.persistence.AdRepository;
import com.eCommerce.persistence.SubCategoryRepository;
import com.eCommerce.persistence.UserRepository;
import com.eCommerce.persistence.VendorRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AdService {

    @Inject
    SubCategoryRepository subCategoryRepository;
    @Inject
    AdRepository adRepository;
    @Inject
    VendorRepository vendorRepository;
    @Inject
    UserRepository userRepository;
    AdMapper adMapper=new AdMapper();
    AdsMapper adsMapper=new AdsMapper();

    @Transactional
    public void addNewProduct(Long id, String subCat, AdDto adDto) throws ResourceNotFoundException {
        Optional<Vendor> vendor=vendorRepository.findByIdOptional(id);
        Vendor vendor_=vendor.orElseThrow(()->new ResourceNotFoundException("no Resource found"));
        Optional<SubCategory>subCategory=subCategoryRepository.find("subcategoryName",subCat).firstResultOptional();
        if (!subCategory.isPresent()){
            throw new ResourceNotFoundException("Resource not found :"+subCat);
        }
        SubCategory subCategory_=subCategory.get();
        Ad ad=adMapper.toEntity(adDto);
        String pid= UUID.randomUUID().toString();
        pid=pid.replaceAll("-","");
        pid=pid.substring(0,7);
        ad.setProductID(pid);
        ad.setPublishDate(new Date());
        ad.setSubCategory(subCategory_);
        ad.setVendor(vendor_);
        vendor_.getAdList().add(ad);
        subCategory_.getAdList().add(ad);
        adRepository.persist(ad);
    }
    // change Ad data by ID

    public AdDto updateProductInfo(Long id,AdDto newAd) throws ResourceNotFoundException{
        Ad ad_= adRepository.findByIdOptional(id).map(ad -> {
            ad.setBrand(newAd.getBrand());
            ad.setDescription(newAd.getDescription());
            ad.setProductName(newAd.getProductName());
            ad.setPrice(newAd.getPrice());
            ad.setQuantity(newAd.getQuantity());
            ad.setSku(newAd.getSku());
            ad.setItemCondition(ad.getItemCondition());
            ad.setProductStatus(newAd.getProductStatus());
            ad.persist();
            return ad;

        }).orElseThrow(()->new ResourceNotFoundException("Ad with "+id+ "not found !"));
        return adMapper.toDto(ad_);
    }
// find ads by name of Brand

    public List<Ads> loadAdsByBrandName(String brand)throws ResourceNotFoundException{
        List<Ad>adList=adRepository.find("brand",brand).list();
        if (adList.isEmpty()){
            throw new ResourceNotFoundException("Ad with Brand "+brand+" not found !");
        }
        return adsMapper.toDto(adList);
    }
// find all ads by Name of Company
    public List<Ads>findAdsByCompanyName(String companyName)throws ResourceNotFoundException{
       List<Ad>adList=vendorRepository.find("companyName",companyName).singleResultOptional().map(Vendor::getAdList).orElseThrow(()->new ResourceNotFoundException("Resource with Name "+companyName+ "not found !"));
       return adsMapper.toDto(adList);

    }
// show all ads of sub category

    public List<Ads>findAdsBySubCategory(String subcategoryName)throws ResourceNotFoundException{
        List<Ad>adList=subCategoryRepository.find("subcategoryName",subcategoryName).singleResultOptional().map(SubCategory::getAdList).orElseThrow(()->new ResourceNotFoundException("Resource with Sub-Category :"+subcategoryName+ " not found !"));
        return adsMapper.toDto(adList);
    }
 // show By Name of Product  and price between

    public List<Ads>fetchProductsByNameAndPrice(String name,double min,double max)throws ResourceNotFoundException{
        List<Ad>ads=adRepository.findAdByName(name).stream().toList();
        if (ads.isEmpty()){
            throw new ResourceNotFoundException("List is Empty!");
        }
        ads=Ad.find("price >= min and price <= max", Parameters.with("min",min).and("max",max)).list();
        return adsMapper.toDto(ads);

    }
    //show By name and Condition of Item

    public List<Ads>fetchAdsByNameAndItemCondition(String name, ItemCondition itemCondition,int index,int size)throws ResourceNotFoundException{
        PanacheQuery<Ad> adList=Ad.find("productName and itemCondition",Parameters.with("productName",name).and("itemCondition",itemCondition));
        List<Ad>ads=adList.page(index,size).list();
        if (ads.isEmpty()){
            throw new ResourceNotFoundException("List is Empty!");
        }
     return adsMapper.toDto(ads);
    }
    public List<Ads>fetchAdsBy(String name,
                               ItemCondition itemCondition,
                               String ort,
                               double min,
                               double max,
                               int index,
                               int size)throws ResourceNotFoundException{

        PanacheQuery<Ad>adPanacheQuery=Ad.find("productName = : name and itemCondition = : itemCondition and ort = : ort price >= min and price <= max",Parameters.with("name",name).and("itemCondition",itemCondition).and("ort",ort).and("min",min).and("max",max));
        List<Ad>ads=adPanacheQuery.page(index,size).list();
        if (ads.isEmpty()){
            throw new ResourceNotFoundException("List is empty !");
        }
      return adsMapper.toDto(ads);
    }

    public void deleteAdById(Long id)throws ResourceNotFoundException{
        if (adRepository.findByIdOptional(id).isEmpty()){
            throw new ResourceNotFoundException("Resource not found !");
        }
        adRepository.deleteById(id);
    }
}
