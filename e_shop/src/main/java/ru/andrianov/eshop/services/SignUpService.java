package ru.andrianov.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.repositories.CustomerRepository;


@Service
@RequiredArgsConstructor
public class SignUpService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole("ROLE_USER");
        customerRepository.save(customer);
    }
}
