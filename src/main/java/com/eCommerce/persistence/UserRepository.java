package com.eCommerce.persistence;

import com.eCommerce.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User>findByUserEmail(String email){
        return User.find("email",email).singleResultOptional();
    }
    public Optional<User>findUserByToken(String token){
        return User.find("token",token).singleResultOptional();
    }
}
