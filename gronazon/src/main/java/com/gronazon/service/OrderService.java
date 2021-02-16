package com.gronazon.service;

import io.quarkus.panache.common.Page;
import com.gronazon.domain.Order;
import com.gronazon.service.dto.OrderDTO;
import com.gronazon.service.mapper.OrderMapper;
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
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Inject
    OrderMapper orderMapper;

    @Transactional
    public OrderDTO persistOrUpdate(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        var order = orderMapper.toEntity(orderDTO);
        order = Order.persistOrUpdate(order);
        return orderMapper.toDto(order);
    }

    /**
     * Delete the Order by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        Order.findByIdOptional(id).ifPresent(order -> {
            order.delete();
        });
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return Order.findByIdOptional(id)
            .map(order -> orderMapper.toDto((Order) order)); 
    }

    /**
     * Get all the orders.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<OrderDTO> findAll(Page page) {
        log.debug("Request to get all Orders");
        return new Paged<>(Order.findAll().page(page))
            .map(order -> orderMapper.toDto((Order) order));
    }



}
