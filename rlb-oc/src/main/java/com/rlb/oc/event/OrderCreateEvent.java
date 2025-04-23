package com.rlb.oc.event;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.dto.ProductDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderCreateEvent {
    private Integer id;
    private Integer custId;
    private Date orderDate;
    private List<ProductDto> productList = new ArrayList<>();
    OrderStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<ProductDto> getList() {
        return productList;
    }

    public void setList(List<ProductDto> productList) {
        this.productList = productList;
    }
}
