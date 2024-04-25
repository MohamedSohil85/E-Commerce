package com.eCommerce.services;

import com.eCommerce.dtos.accountdto.CustomerDto;
import com.eCommerce.entities.Address;
import com.eCommerce.entities.Customer;
import com.eCommerce.entities.ShoppingCart;
import com.eCommerce.entities.User;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.CustomerMapper;
import com.eCommerce.persistence.AddressRepository;
import com.eCommerce.persistence.CustomerRepository;
import com.eCommerce.persistence.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    AddressRepository addressRepository;
    CustomerMapper customerMapper=new CustomerMapper();
    @Inject
    UserRepository userRepository;
    @Inject
    Mailer mailer;


    @Transactional
    public void saveNewCustomer(CustomerDto customerDto){

      Optional<Customer>customerOptional=customerRepository.findCustomerByEmail(customerDto.getEmail());


      Customer customer=customerMapper.toEntity(customerDto);
      customer.setRole("customer");
      String random= UUID.randomUUID().toString();
      random=random.replaceAll("-","");
        customer.setToken(random);

        random=random.substring(0,3);
      String username= customerDto.getFirstName().substring(0,2)+random+customerDto.getLastName().substring(0,2);
        customer.setUsername(username);


        //
        customer.setDateOfRegistration(new Date());
        String pwd= UUID.randomUUID().toString();
        pwd=pwd.replaceAll("-","");
        pwd=pwd.substring(0,7);
        String encode_pwd=BcryptUtil.bcryptHash(pwd);
        mailer.send(Mail.withText(customerDto.getEmail(),"Username","Dear Customer your Username is :"+customer.getUsername()+" your Password :"+pwd));
        customer.setPassword(encode_pwd);
        Address address=new Address();
        address.setCity(customerDto.getCity());
        address.setZip(customerDto.getZip());
        address.setStreet(customerDto.getStreet());
        address.setCountry(customerDto.getCountry());
        address.setState(customerDto.getState());
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setCartCode("cart-"+username);
        customer.setShoppingCart(shoppingCart);
        shoppingCart.setUser(customer);
        shoppingCart.persist();
        customer.setAddress(address);
        address.setUser(customer);
        customer.persist();
        address.persist();
    }
    @Transactional
    public CustomerDto changeCustomerDetailsById(Long id,CustomerDto customerDto)throws ResourceNotFoundException{
      Customer customer_=    customerRepository.findByIdOptional(id).map(customer -> {
          customer.setPhoneNumber(customerDto.getPhoneNumber());

          customer.setEmail(customerDto.getEmail());
          Address address=Address.find("user_id",id).singleResult();
          address.setCity(customerDto.getCity());
          address.setZip(customerDto.getZip());
          address.setStreet(customerDto.getStreet());
          address.setCountry(customerDto.getCountry());
          address.setState(customerDto.getState());
          address.setUser(customer);
          customer.setAddress(address);
          customer.persist();
          return customer;

      }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
      return customerMapper.toDto(customer_);
    }
    // forget my password
    @Transactional
    public User sendPasswordByEmail(String email)throws ResourceNotFoundException{
    Optional<User>userOptional= userRepository.findByUserEmail(email);
    User user=userOptional.orElseThrow(()->new ResourceNotFoundException("Object not found !"));
    String newPassword=UUID.randomUUID().toString();
    newPassword=newPassword.replaceAll("-","");
    newPassword=newPassword.substring(0,7);
    mailer.send(Mail.withText(user.getEmail(),"new password","your new password :"+newPassword));
    String encodedPW=BcryptUtil.bcryptHash(newPassword);
    user.setPassword(encodedPW);
    user.persist();;
    return user;

    }
    @Transactional
    public User changeMyPassword(String token ,User user)throws ResourceNotFoundException{
      User user_= userRepository.findUserByToken(token).map(u_ ->{
            String newPassword=user.getPassword();
            String encoded_pw=BcryptUtil.bcryptHash(newPassword);
            u_.setPassword(encoded_pw);
             userRepository.persist(u_);
             return u_;
        } ).orElseThrow(()->new ResourceNotFoundException("Resource not found"));

        return user_;

    }
    // get customers by keyword

    public List<CustomerDto> getListOfCustomersStartWith(String keyword)throws ResourceNotFoundException {
        List<Customer>customers=customerRepository.listAll().stream().filter(customer -> customer.getLastName().startsWith(keyword)).collect(Collectors.toList());
        if (customers.isEmpty()){
            throw new ResourceNotFoundException("List is Empty");
        }

        return customerMapper.toDto(customers);
    }
    public List<CustomerDto>loadAllCustomers()throws ResourceNotFoundException{
        List<Customer>customers=customerRepository.listAll(Sort.by("lastName"));
        if (customers.isEmpty()){
            throw new ResourceNotFoundException("List is Empty");
        }
        return customerMapper.toDto(customers);
    }



    //
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByUserEmail(email);
    }
    @Transactional
    public void deleteUserById(Long id)throws ResourceNotFoundException{

        userRepository.findByIdOptional(id).ifPresent(user -> {
            userRepository.delete(user);

        });

        Optional<User>userOptional=userRepository.findByIdOptional(id);
        User user=userOptional.orElseThrow(()->new ResourceNotFoundException("Customer not found !"));
        userRepository.delete(user);
    }

}
