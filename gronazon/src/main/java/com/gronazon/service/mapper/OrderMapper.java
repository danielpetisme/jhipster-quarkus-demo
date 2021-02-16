package com.gronazon.service.mapper;


import com.gronazon.domain.*;
import com.gronazon.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "cdi", uses = {CustomerMapper.class, ProductMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.email", target = "customerEmail")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.title", target = "productTitle")
    OrderDTO toDto(Order order);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "productId", target = "product")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.id = id;
        return order;
    }
}
