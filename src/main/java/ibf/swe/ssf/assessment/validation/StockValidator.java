package ibf.swe.ssf.assessment.validation;

import java.util.List;

import ibf.swe.ssf.assessment.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockValidator implements ConstraintValidator<Stock, String> {

    @Override
    public boolean isValid(String item, ConstraintValidatorContext context) {

        if (null != item) {
            
            List<String> stock = Constants.STOCK;

            if (!stock.contains(item)) {
                return false;
            }
        }


        return true;

    }
    
}
