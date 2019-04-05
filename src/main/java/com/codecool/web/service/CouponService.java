package com.codecool.web.service;

import com.codecool.web.model.Coupon;
import com.codecool.web.model.Shop;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface CouponService {

    List<Coupon> getCoupons() throws SQLException;

    Coupon getCoupon(String id) throws SQLException, ServiceException;

    Coupon addCoupon(String name, String percentage) throws SQLException, ServiceException;

    void addCouponToShops(String couponId, String... shopIds) throws SQLException, ServiceException;

    List<Shop> getCouponShops(String id) throws SQLException, ServiceException;
}
