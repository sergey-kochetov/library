package ru.com.melt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.com.melt.model.Customer;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager manager;

    @Test(expected = NullPointerException.class)
    public void whenAddNullCustomer_thenThrowExc() {
        // given
        Customer customer = createUser(null);

        // when
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findAll();

        // then
        assertEquals("", customers.toString());
    }

    @Test
    public void whenAddTwoCustomer_thenReturnList() {
        // given
        Customer customer1 = createUser("username1");
        Customer customer2 = createUser("username2");

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<String> customers = customerRepository.findAll().stream()
                .map(Customer::getUsername)
                .collect(Collectors.toList());

        // then
        assertEquals(2, customers.size());
        assertEquals("[username1, username2]", customers.toString());
    }

    @Test
    public void whenFindCustomer_thenOk() {
        // given
        Customer customer1 = createUser("username1");
        Customer customer2 = createUser("username2");

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        Customer customer = customerRepository.findCustomerByUsername("username1").get();

        // then
        assertEquals("username1", customer.getUsername());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenFindCustomer_thenThrowExc() {
        // given
        Customer customer1 = createUser("username1");
        Customer customer2 = createUser("username2");

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        Customer customer = customerRepository.findCustomerByUsername("username").get();

        // then
    }

    private Customer createUser(String name) {
        Customer user = new Customer(name);

        return manager.persist(user);
    }


}