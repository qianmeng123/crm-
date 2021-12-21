package com.xzj.dao;

import com.xzj.domain.Customer;

import java.util.List;

    public interface CustomerDao {

    Customer getCustmoerName(String company);

    int save(Customer customer);

    List<String> getNameList(String name);

    Customer getNameId(String customerName);
}
