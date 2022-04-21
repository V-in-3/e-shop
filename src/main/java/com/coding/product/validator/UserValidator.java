package com.coding.product.validator;

import com.coding.product.models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import  org.springframework.validation.Validator;

import com.coding.product.repositories.UserRepository;

@Component
public class UserValidator implements Validator {
	private final UserRepository uRepo;

    public UserValidator(UserRepository uRepo) {
        this.uRepo = uRepo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
	@Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        if(this.uRepo.findByEmail(user.getEmail()) != null) {
        	errors.rejectValue("email", "Unique", "User with such an email is registered already");
        }

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match", "The password is not correct");
        }

    }
}
