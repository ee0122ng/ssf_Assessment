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
import ibf.swe.ssf.assessment.model.Invoice;
import ibf.swe.ssf.assessment.model.Quotation;
import ibf.swe.ssf.assessment.service.CartService;
import ibf.swe.ssf.assessment.service.QuotationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path={"/"})
public class PurchaseOrderController {

    @Autowired
    CartService cartSvc;

    @Autowired
    QuotationService quoteSvc;
    
    @GetMapping
    public String getHome(Model model, HttpSession session) {

        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
        if (null == sessionCart) {
            sessionCart = new LinkedList<>();
            session.setAttribute("cartList", sessionCart);
        }
        
        model.addAttribute("cart", new Cart());

        return "view1";

    }

    @PostMapping(path={"/add"})
    public String addItem(@Valid Cart cart, BindingResult result ,Model model, HttpSession session) {

        System.out.println(">> cart from home: " + cart.toString());

        if (result.hasErrors()) {

            return "view1";
        }

        // add the cart to cart list
        cartSvc.addCart(cart);
        List<Cart> cartList = cartSvc.retrieveCartList();
        
        // update session cart
        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
        sessionCart.add(cart);

        model.addAttribute("cartList", cartList);

        return "view1";
    }

    @GetMapping()
    @RequestMapping(path={"/shippingaddress"})
    public String proceedToShipping(Model model, HttpSession session) {

        model.addAttribute("customer", new Customer());

        return "view2";
    }

    @PostMapping(path={"/shippingaddress"})
    public String processShipping(@Valid Customer customer, BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            return "view2";
        }

        // pass the customer details to session
        session.setAttribute("customer", new Customer());
        Customer sessionCustomer = (Customer) session.getAttribute("customer");
        sessionCustomer.setName(customer.getName());
        sessionCustomer.setAddress(customer.getAddress());

        model.addAttribute("customer", customer);

        return "redirect:/quotation";

    }

    // @PostMapping(path={"/shippingaddress"})
    // public String enterShippingDetails(@Valid Customer customer, BindingResult result, Model model, HttpSession session) {

    //     // validate the cart
    //     List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
    //     if (null == sessionCart) {
    //         return "view1";
    //     }

    //     // if customer details not valid, stay on the page
    //     if (result.hasErrors()) {
    //         return "view2";
    //     }

    //     return "view2";
    // }

    @PostMapping(path={"/quotation"})
    public String getQuotation(Model model, HttpSession session) {

        // retrieve cart list from the session
        List<Cart> sessionCart = (List<Cart>) session.getAttribute("cartList");
        Customer sessionCustomer = (Customer) session.getAttribute("customer");

        // retrieve items from cart list
        List<String> itemList = quoteSvc.getItemsFromCartList(sessionCart);

        try {
            Quotation quote = quoteSvc.getQuotation(itemList);

            Float total = cartSvc.getTotalCost(sessionCart, quote);

            // Update invoice details
            Invoice invoice = new Invoice();
            invoice.setId(quote.getQuoteId());
            invoice.setName(sessionCustomer.getName());
            invoice.setAddress(sessionCustomer.getAddress());
            invoice.setTotal(total);

            model.addAttribute("invoice", invoice);

            return "view3";

        } catch (Exception ex) {

            model.addAttribute("customer", sessionCustomer);
            model.addAttribute("error", ex.getMessage());
                
            return "view2";
        }
        
    }



    
}
