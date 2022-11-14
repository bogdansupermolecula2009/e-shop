package ru.andrianov.eshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.models.Item;
import ru.andrianov.eshop.repositories.CustomerRepository;
import ru.andrianov.eshop.security.CustomerDetails;
import ru.andrianov.eshop.services.AdminService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AdminService adminService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/userInfo")
    public String showUserInfo(Model model, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        Customer customer = customerRepository.getReferenceById(customerDetails.getCustomer().getId());
        List<Customer> user = new ArrayList<>();
        user.add(customer);
        model.addAttribute("user", user);
        return "userInfo";
    }

    @GetMapping("/admin/allUsers")
    public String showAllUsers(Model model) {
        adminService.doAdminStuff();
        List<Customer> users = customerRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bucket/save")
    public String saveItemToBucket(@RequestParam long itemId, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        Customer customer = customerRepository.getReferenceById(customerDetails.getCustomer().getId());
        customer.getItems().add(new Item(itemId));
        customerRepository.save(customer);
        return "redirect:/userInfo";
    }
}
