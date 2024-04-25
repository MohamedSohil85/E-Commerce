package com.eCommerce.services;

import com.eCommerce.dtos.accountdto.VendorDto;
import com.eCommerce.entities.*;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.VendorMapper;
import com.eCommerce.persistence.AddressRepository;
import com.eCommerce.persistence.UserRepository;
import com.eCommerce.persistence.VendorRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Page;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ApplicationScoped
public class VendorService {

    @Inject
    VendorRepository vendorRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    AddressRepository addressRepository;
    VendorMapper vendorMapper=new VendorMapper();
    @Inject
    Mailer mailer;
 @Transactional
    public void saveNewVendor(VendorDto vendorDto){
        Vendor vendor=vendorMapper.toEntity(vendorDto);
        vendor.setDateOfRegistration(new Date());
        vendor.setRole("vendor");

        String random= UUID.randomUUID().toString();
        random=random.replaceAll("-","");
        vendor.setToken(random);
        //
        random=random.substring(0,3);
        String username= vendorDto.getFirstName().substring(0,2)+random+vendorDto.getLastName().substring(0,2);
        vendor.setUsername(username);
        //

        String pwd= UUID.randomUUID().toString();
        pwd=pwd.replaceAll("-","");
        pwd=pwd.substring(0,7);
        String encode_pwd= BcryptUtil.bcryptHash(pwd);
        mailer.send(Mail.withText(vendorDto.getEmail(),"Username","Dear Vendor your Username is :"+vendor.getUsername()+" your Password :"+pwd));
        vendor.setPassword(encode_pwd);

        Address address=new Address();
        address.setState(vendorDto.getState());
        address.setCountry(vendorDto.getCountry());
        address.setStreet(vendorDto.getStreet());
        address.setZip(vendorDto.getZip());
        address.setUser(vendor);
        vendor.setAddress(address);
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setCartCode("cart-"+username);
        shoppingCart.setUser(vendor);
        vendor.setShoppingCart(shoppingCart);
        shoppingCart.persist();
        address.persist();
        vendor.persist();
    }


  public List<VendorDto>loadAllVendors(int index,int size)throws ResourceNotFoundException{
      PanacheQuery<Vendor>customerPanacheQuery=Customer.findAll(Sort.by("lastName")).page(Page.of(index, size));
        List<Vendor>vendors=customerPanacheQuery.list();

        if (vendors.isEmpty()){
            throw new ResourceNotFoundException("List is Empty");
        }
        return vendorMapper.toDto(vendors);
  }

public VendorDto changeVendorDataById(Long id,VendorDto vendorDto)throws ResourceNotFoundException{
        Vendor vendor=vendorRepository.findByIdOptional(id).map(vendor_->{
            vendor_.setFax(vendorDto.getFax());
            vendor_.setCompanyName(vendorDto.getCompanyName());
            vendor_.setPhoneNumber(vendorDto.getPhoneNumber());
            vendor_.setWebsite(vendorDto.getWebsite());
            vendor_.setEmail(vendor_.getEmail());
            Address address=Address.find("user_id",id).singleResult();
            address.setCity(vendorDto.getCity());
            address.setZip(vendorDto.getZip());
            address.setState(vendorDto.getState());
            address.setStreet(vendorDto.getStreet());
            address.setCountry(vendorDto.getCountry());
            vendor_.persist();
            return vendor_;
        }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
        return vendorMapper.toDto(vendor);
}
     public Optional<User> findUserByEmail(String email){
       return userRepository.findByUserEmail(email);

     }
}
