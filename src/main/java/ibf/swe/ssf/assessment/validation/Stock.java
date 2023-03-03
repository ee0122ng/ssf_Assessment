package ibf.swe.ssf.assessment.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=StockValidator.class)
public @interface Stock {
    
    String message() default "We do not sotck ";
    // to be included together with a @Constraint annotation
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
