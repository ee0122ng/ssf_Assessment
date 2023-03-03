package ibf.swe.ssf.assessment.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ibf.swe.ssf.assessment.model.Cart;
import ibf.swe.ssf.assessment.model.Quotation;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class QuotationService {

    private static final String URL = "https://quotation.chuklee.com";

    public Quotation getQuotation(List<String> items) throws Exception {

        // convert list of items to JsonObject  
        JsonArray jArray = Json.createArrayBuilder().add(items.toString()).build();

        // call rest API
        RequestEntity<String> req = RequestEntity
            .post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jArray.toString(), String.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> rep = template.exchange(req, String.class);

        Integer status = rep.getStatusCode().value();

        if (status == 200) {
            String payload = rep.getBody();

            // create JsonObject for the Qsystem
            JsonReader jReader = Json.createReader(new StringReader(payload));
            JsonObject jObj = jReader.readObject();

            Quotation quote = new Quotation();
            quote.setQuoteId(jObj.getString("quoteId"));
            
            // create JsonObject for the quotation
            JsonObject jQuote = jObj.getJsonObject("quotations");
            for (String i : items) {
                quote.addQuotation(i, (float) jQuote.getJsonNumber(i).doubleValue());
            }

            return quote;

        }

        return null;

    }

    public List<String> getItemsFromCartList(List<Cart> cartList) {
        
        List<String> itemList = new LinkedList<>();
        
        for (Cart cart : cartList) {
            itemList.add(cart.getItem());
        }

        return itemList;
    }
    
}
