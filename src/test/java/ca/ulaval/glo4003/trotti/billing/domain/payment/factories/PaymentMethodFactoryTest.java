package ca.ulaval.glo4003.trotti.billing.domain.payment.factories;

import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethod;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodFactoryTest {
	
	private static final String VALID_CARD_NUMBER = "4111111111111111";
	private static final String VALID_CARD_HOLDER = "Equipe Archi";
	private static final String INVALID_CARD_NUMBER = "123456789";
	
	@Mock
	private DataCodec dataCodec;
	
	private PaymentMethodFactory paymentMethodFactory;
	
	@BeforeEach
	void setup() {
		paymentMethodFactory = new PaymentMethodFactory(dataCodec);
	}
	
	@Test
	void givenValidCardAndFutureDate_whenCreateCreditCard_ThenReturnPaymentMethod() {
		YearMonth futureDate = YearMonth.now().plusMonths(1);
		
		PaymentMethod result = paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER, VALID_CARD_HOLDER, futureDate);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	void givenInvalidCardNumber_whenCreateCreditCard_thenThrowException(){
		YearMonth futureDate = YearMonth.now().plusMonths(1);
		
		Executable executable = () -> paymentMethodFactory.createCreditCard(INVALID_CARD_NUMBER, VALID_CARD_HOLDER, futureDate);
		
		Assertions.assertThrows(InvalidParameterException.class, executable);
	}
	
	@Test
	void givenExpiredCard_whenCreateCreditCard_thenThrowException(){
		YearMonth expiredDate = YearMonth.now().minusMonths(1);
		
		Executable executable = () -> paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER, VALID_CARD_HOLDER, expiredDate);
		
		InvalidParameterException exception = Assertions.assertThrows(InvalidParameterException.class, executable);
		Assertions.assertEquals("Expired credit card", exception.getMessage());
	}
	
	@Test
	void givenCurrentMonthExpirationDate_whenCreateCreditCard_thenReturnPaymentMethod(){
		YearMonth currentMonth = YearMonth.now();
		
		PaymentMethod result =  paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER, VALID_CARD_HOLDER, currentMonth);
		
		Assertions.assertNotNull(result);
	}
}
