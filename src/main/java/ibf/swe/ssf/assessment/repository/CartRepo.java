package ibf.swe.ssf.assessment.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ibf.swe.ssf.assessment.model.Cart;

@Repository
public class CartRepo {

    private List<Cart> cartList = new LinkedList<>();

    public List<Cart> getCartList() {
        return this.cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    
    public void addCart(Cart cart) {

        this.cartList.add(cart);
    }
    
}
