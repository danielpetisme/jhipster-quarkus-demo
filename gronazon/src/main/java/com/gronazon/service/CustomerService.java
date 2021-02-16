package com.gronazon.service;

import io.quarkus.panache.common.Page;
import com.gronazon.domain.Customer;
import com.gronazon.service.dto.CustomerDTO;
import com.gronazon.service.mapper.CustomerMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    CustomerMapper customerMapper;

    @Transactional
    public CustomerDTO persistOrUpdate(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        var customer = customerMapper.toEntity(customerDTO);
        customer = Customer.persistOrUpdate(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Delete the Customer by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        Customer.findByIdOptional(id).ifPresent(customer -> {
            customer.delete();
        });
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CustomerDTO> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return Customer.findByIdOptional(id)
            .map(customer -> customerMapper.toDto((Customer) customer)); 
    }

    /**
     * Get all the customers.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<CustomerDTO> findAll(Page page) {
        log.debug("Request to get all Customers");
        return new Paged<>(Customer.findAll().page(page))
            .map(customer -> customerMapper.toDto((Customer) customer));
    }



}
