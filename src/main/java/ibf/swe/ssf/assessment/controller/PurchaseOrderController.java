package ibf.swe.ssf.assessment.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf.swe.ssf.assessment.model.Cart;
import ibf.swe.ssf.assessment.model.Customer;
import ibf.swe.ssf.assessment.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path={"/"})
public class PurchaseOrderController {

    @Autowired
    CartService cartSvc;
    
    @GetMapping
    public String getHome(Model model, HttpSession session) {

        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
        if (null == sessionCart) {
            session.setAttribute("cartList", new LinkedList<Cart>());
        }
        
        model.addAttribute("cart", new Cart());

        return "view1";

    }

    @PostMapping(path={"/add"})
    public String addItem(@Valid Cart cart, BindingResult result ,Model model, HttpSession session) {

        // cart.setItem("apple");
        // cart.setQuantity(10);

        System.out.println(">> cart from home: " + cart.toString());

        if (result.hasErrors()) {

            return "view1";
        }

        // add the cart to cart list
        cartSvc.addCart(cart);
        List<Cart> cartList = cartSvc.retrieveCartList();
        // check cart repo
        for (Cart ct : cartList) {
            System.out.println(">>> cart repo : " + ct.toString());
        }
        
        // update session cart
        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
        sessionCart.add(cart);
        System.out.println(">> session cart size: " + sessionCart.size());

        model.addAttribute("cart", new Cart());
        model.addAttribute("cartList", cartList);

        return "redirect:/";
    }

    @GetMapping(path={"/shippingaddress"})
    public String enterDetails(Model model, HttpSession session) {

        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");

        model.addAttribute("customer", new Customer());

        return "view2";
    }
    
}
