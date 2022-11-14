package ru.andrianov.eshop.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.services.CustomerDetailsService;


@Component
public class CustomerValidator implements Validator {

    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public CustomerValidator(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        try {
            customerDetailsService.loadUserByUsername(customer.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Пользователь с таким именем пользователя уже существует");
    }
}
