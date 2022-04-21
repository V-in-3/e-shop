package com.coding.product.validator;

import com.coding.product.models.User;
import com.coding.product.models.UserRole;
import com.coding.product.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserValidatorTest {
  @MockBean UserRepository uRepo;

  @Test
  @DisplayName("Test not validateUserByEmail Success")
  void notValidateUserByEmail() {
    UserValidator validator = new UserValidator(uRepo);
    User user = new User(1L, "user name", "user@gmail.com", "12345678", UserRole.AUTHORIZED_USER, "12345678");
    Errors errors = new BeanPropertyBindingResult(user, "user");
    when(uRepo.findByEmail(any())).thenReturn(user);
    validator.validate(user, errors);

    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("email"));
  }

  @Test
  @DisplayName("Test validateUserByEmail Success")
  void validateUserByEmail() {
    UserValidator validator = new UserValidator(uRepo);
    User user = new User(1L, "name", null, "12345678", UserRole.AUTHORIZED_USER, "12345678");
    Errors errors = new BeanPropertyBindingResult(user, "user");
    when(uRepo.findByEmail(any())).thenReturn(user);
    validator.validate(user, errors);

    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("email"));
  }

  @Test
  @DisplayName("Test validateUserByPassword Success")
  void validateUserByPassword() {
    UserValidator validator = new UserValidator(uRepo);
    User user = new User(1L, "name", null, "12345678", UserRole.AUTHORIZED_USER, "12345678");
    Errors errors = new BeanPropertyBindingResult(user, "user");
    when(uRepo.findByEmail(any())).thenReturn(null);
    validator.validate(user, errors);

    assertFalse(errors.hasErrors());
  }

  @Test
  @DisplayName("Test not validateUserByPassword Success")
  void notValidateUserByPassword() {
    UserValidator validator = new UserValidator(uRepo);
    User user = new User(1L, "name", "name@gmail.com", "12345678", UserRole.AUTHORIZED_USER, "00000000");
    Errors errors = new BeanPropertyBindingResult(user, "user");
    when(uRepo.findByEmail(any())).thenReturn(null);
    validator.validate(user, errors);

    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("passwordConfirmation"));
  }
}
