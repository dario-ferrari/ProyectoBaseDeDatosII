package com.proyecto.cart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cart.entities.Detail;
import com.proyecto.cart.entities.Sale;
import com.proyecto.cart.entities.ShoppingCart;
import com.proyecto.cart.repositories.SaleRepository;
import com.proyecto.cart.security.entities.User;
import com.proyecto.cart.security.services.UserService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**Puente entre repositorios y controladores */

@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService; /**se trae el servicio del carrito de compras para poder buscar todos los elementos que tiene el carrito */
    private final DetailService detailService;
    @Autowired
    public SaleService(SaleRepository saleRepository, UserService userService, ShoppingCartService shoppingCartService,
                       DetailService detailService) {
        this.saleRepository = saleRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.detailService = detailService;
    }

    public List<Sale> getSalesByClient(String userName){
        return this.saleRepository.findByClient_UserName(userName);
    }

    public void createSale(String userName){ /**crear venta en base al cliente */
        User client = this.userService.getByUserName(userName).get();
        List<ShoppingCart> shoppingCartList = this.shoppingCartService.getListByClient(client.getUserName());
        DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        double total = shoppingCartList.stream().mapToDouble(shoppingCartItem -> shoppingCartItem.getProduct().getPrice()
                * shoppingCartItem.getAmount()).sum();/**suma el total del precio de los productos en el carrito */
        Sale sale = new Sale(Double.parseDouble(decimalFormat.format(total)), new Date(), client);
        Sale saveSale = this.saleRepository.save(sale);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            Detail detail = new Detail();
            detail.setProduct(shoppingCart.getProduct());
            detail.setAmount(shoppingCart.getAmount());
            detail.setSale(saveSale);
            this.detailService.createDetail(detail);
        }
        this.shoppingCartService.cleanShoppingCart(client.getId());
    }

}
