package com.codecool.web.servlet;

import com.codecool.web.dao.CouponDao;
import com.codecool.web.dao.ShopDao;
import com.codecool.web.dao.database.DatabaseCouponDao;
import com.codecool.web.dao.database.DatabaseShopDao;
import com.codecool.web.model.Coupon;
import com.codecool.web.model.Shop;
import com.codecool.web.service.CouponService;
import com.codecool.web.service.ShopService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleCouponService;
import com.codecool.web.service.simple.SimpleShopService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/protected/coupon")
public final class CouponServlet extends AbstractServlet {

    // https://www.postgresql.org/docs/current/static/errcodes-appendix.html
    private static final String SQL_ERROR_CODE_UNIQUE_VIOLATION = "23505";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String id = req.getParameter("id");
            Coupon coupon = couponService.getCoupon(id);

            List<Shop> allShops = shopService.getShops();
            List<Shop> couponShops = couponService.getCouponShops(id);
            req.setAttribute("coupon", coupon);
            req.setAttribute("allShops", allShops);
            req.setAttribute("couponShops", couponShops);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ServiceException ex) {
            req.setAttribute("error", ex.getMessage());
        }
        req.getRequestDispatcher("coupon.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CouponDao couponDao = new DatabaseCouponDao(connection);
            ShopDao shopDao = new DatabaseShopDao(connection);
            CouponService couponService = new SimpleCouponService(couponDao, shopDao);

            String couponId = req.getParameter("id");
            String[] shopIds = req.getParameterValues("shopIds");

            couponService.addCouponToShops(couponId, shopIds);

            String info = String.format("Coupon with id %s has been added to shops with ids: %s",
                couponId, Arrays.stream(shopIds).collect(Collectors.joining(", ")));
            req.setAttribute("info", info);
        } catch (SQLException ex) {
            if (SQL_ERROR_CODE_UNIQUE_VIOLATION.equals(ex.getSQLState())) {
                req.setAttribute("error", "Coupon has been already added to one of the selected shops");
            } else {
                throw new ServletException(ex);
            }
        } catch (ServiceException ex) {
            req.setAttribute("error", ex.getMessage());
        }
        doGet(req, resp);
    }
}
