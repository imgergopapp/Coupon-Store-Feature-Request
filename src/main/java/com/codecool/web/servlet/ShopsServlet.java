package com.codecool.web.servlet;

import com.codecool.web.dao.ShopDao;
import com.codecool.web.dao.database.DatabaseShopDao;
import com.codecool.web.model.Shop;
import com.codecool.web.service.ShopService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleShopService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/shops")
public final class ShopsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);

            List<Shop> shops = shopService.getShops();

            req.setAttribute("shops", shops);
            req.getRequestDispatcher("shops.jsp").forward(req, resp);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ShopDao shopDao = new DatabaseShopDao(connection);
            ShopService shopService = new SimpleShopService(shopDao);

            String name = req.getParameter("name");

            Shop shop = shopService.addShop(name);

            String info = String.format("Shop %s with id %s has been created", shop.getName(), shop.getId());
            req.setAttribute("info", info);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ServiceException ex) {
            req.setAttribute("error", ex.getMessage());
        }
        doGet(req, resp);
    }
}
