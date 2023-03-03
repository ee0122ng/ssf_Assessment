package ibf.swe.ssf.assessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.swe.ssf.assessment.Constants;
import ibf.swe.ssf.assessment.model.Cart;
import ibf.swe.ssf.assessment.repository.CartRepo;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

    public Boolean checkValidItem(Cart cart) {
        
        return Constants.STOCK.contains(cart.getItem());
    }

    public void addCart(Cart cart) {
        
        cartRepo.addCart(cart);

    }

    public List<Cart> retrieveCartList() {

        return cartRepo.getCartList();
    }
    
}
