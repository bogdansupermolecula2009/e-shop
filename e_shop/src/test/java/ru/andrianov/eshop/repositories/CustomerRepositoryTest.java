package ru.andrianov.eshop.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.andrianov.eshop.models.Customer;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void test() throws Exception {
        Customer customer = new Customer(1L, "Michail", 1999, null, "qwerty", "ROLE_USER");
        repository.save(customer);
        Assert.assertNotNull(repository.findByUsername("Michail"));
    }


}