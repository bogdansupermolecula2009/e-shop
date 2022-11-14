package ru.andrianov.eshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.andrianov.eshop.models.Customer;
import ru.andrianov.eshop.repositories.CustomerRepository;
import ru.andrianov.eshop.security.CustomerDetails;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(s);

        if (customer.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new CustomerDetails(customer.get());
    }
}
