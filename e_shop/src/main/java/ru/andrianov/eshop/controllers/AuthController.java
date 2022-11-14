package ru.andrianov.eshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.services.SignUpService;
import ru.andrianov.eshop.validate.CustomerValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final SignUpService signUpService;
    private final CustomerValidator customerValidator;

    @Autowired
    public AuthController(SignUpService signUpService, CustomerValidator customerValidator) {
        this.signUpService = signUpService;
        this.customerValidator = customerValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("customer") Customer customer) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("customer") @Valid Customer customer,
                                      BindingResult bindingResult) {
        customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        signUpService.register(customer);

        return "redirect:/auth/login";
    }
}
