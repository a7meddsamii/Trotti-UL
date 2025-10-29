package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.application.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Cart;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.order.domain.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartApplicationServiceTest {

    private BuyerRepository buyerRepository;
    private PassMapper passMapper;
    private PassFactory passFactory;
    private Buyer buyer;
    private Cart cart;
    private Idul idul;
    private Pass pass1;
    private Pass pass2;
    private PassDto passDto1;
    private PassDto passDto2;
    private PassId passId;
    private MaximumDailyTravelTime maximumDailyTravelTime;
    private Session session;
    private BillingFrequency billingFrequency;

    private CartApplicationService cartApplicationService;

    @BeforeEach
    void setUp() {
        buyerRepository = Mockito.mock(BuyerRepository.class);
        passMapper = Mockito.mock(PassMapper.class);
        passFactory = Mockito.mock(PassFactory.class);
        buyer = Mockito.mock(Buyer.class);
        cart = Mockito.mock(Cart.class);
        idul = Mockito.mock(Idul.class);
        pass1 = Mockito.mock(Pass.class);
        pass2 = Mockito.mock(Pass.class);
        passDto1 = Mockito.mock(PassDto.class);
        passDto2 = Mockito.mock(PassDto.class);
        passId = Mockito.mock(PassId.class);
        maximumDailyTravelTime = Mockito.mock(MaximumDailyTravelTime.class);
        session = Mockito.mock(Session.class);
        billingFrequency = Mockito.mock(BillingFrequency.class);
        cartApplicationService =
                new CartApplicationService(buyerRepository, passMapper, passFactory);
    }

    @Test
    void givenValidIdul_whenGetCart_thenReturnsPassDtoList() {
        List<Pass> passes = Arrays.asList(pass1, pass2);
        List<PassDto> expectedDtos = Arrays.asList(passDto1, passDto2);
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getCart()).thenReturn(cart);
        Mockito.when(cart.getPasses()).thenReturn(passes);
        Mockito.when(passMapper.toDto(pass1)).thenReturn(passDto1);
        Mockito.when(passMapper.toDto(pass2)).thenReturn(passDto2);

        List<PassDto> result = cartApplicationService.getCart(idul);

        Assertions.assertEquals(expectedDtos, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyer).getCart();
        Mockito.verify(cart).getPasses();
        Mockito.verify(passMapper).toDto(pass1);
        Mockito.verify(passMapper).toDto(pass2);
    }

    @Test
    void givenValidIdulAndPassDtos_whenAddToCart_thenPassesAreAddedAndCartIsReturned() {
        List<PassDto> passDtos = Arrays.asList(passDto1, passDto2);
        List<Pass> passes = Arrays.asList(pass1, pass2);
        List<PassDto> expectedResult = Arrays.asList(passDto1, passDto2);

        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(passDto1.maximumDailyTravelTime()).thenReturn(maximumDailyTravelTime);
        Mockito.when(passDto1.session()).thenReturn(session);
        Mockito.when(passDto1.billingFrequency()).thenReturn(billingFrequency);
        Mockito.when(passDto2.maximumDailyTravelTime()).thenReturn(maximumDailyTravelTime);
        Mockito.when(passDto2.session()).thenReturn(session);
        Mockito.when(passDto2.billingFrequency()).thenReturn(billingFrequency);
        Mockito.when(passFactory.create(maximumDailyTravelTime, session, billingFrequency))
                .thenReturn(pass1).thenReturn(pass2);
        Mockito.when(buyer.getCart()).thenReturn(cart);
        Mockito.when(cart.getPasses()).thenReturn(passes);
        Mockito.when(passMapper.toDto(pass1)).thenReturn(passDto1);
        Mockito.when(passMapper.toDto(pass2)).thenReturn(passDto2);

        List<PassDto> result = cartApplicationService.addToCart(idul, passDtos);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyer).addToCart(pass1);
        Mockito.verify(buyer).addToCart(pass2);
        Mockito.verify(buyerRepository).update(buyer);
        Mockito.verify(passFactory, Mockito.times(2)).create(maximumDailyTravelTime, session,
                billingFrequency);
    }

    @Test
    void givenValidIdulAndPassId_whenRemoveFromCart_thenPassIsRemovedAndCartIsReturned() {
        List<Pass> remainingPasses = Collections.singletonList(pass1);
        List<PassDto> expectedResult = Collections.singletonList(passDto1);
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getCart()).thenReturn(cart);
        Mockito.when(cart.getPasses()).thenReturn(remainingPasses);
        Mockito.when(passMapper.toDto(pass1)).thenReturn(passDto1);

        List<PassDto> result = cartApplicationService.removeFromCart(idul, passId);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyer).removeFromCart(passId);
        Mockito.verify(buyerRepository).update(buyer);
    }

    @Test
    void givenValidIdul_whenClearCart_thenCartIsClearedAndBuyerIsSaved() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);

        cartApplicationService.clearCart(idul);

        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyer).clearCart();
        Mockito.verify(buyerRepository).update(buyer);
    }

    @Test
    void givenEmptyCart_whenGetCart_thenReturnsEmptyList() {
        List<Pass> emptyPasses = Collections.emptyList();
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getCart()).thenReturn(cart);
        Mockito.when(cart.getPasses()).thenReturn(emptyPasses);

        List<PassDto> result = cartApplicationService.getCart(idul);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyer).getCart();
        Mockito.verify(cart).getPasses();
    }

    @Test
    void givenEmptyPassList_whenAddToCart_thenNoPassesAreAddedAndCurrentCartIsReturned() {
        List<PassDto> emptyPassDtos = Collections.emptyList();
        List<Pass> currentPasses = Collections.singletonList(pass1);
        List<PassDto> expectedResult = Collections.singletonList(passDto1);

        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getCart()).thenReturn(cart);
        Mockito.when(cart.getPasses()).thenReturn(currentPasses);
        Mockito.when(passMapper.toDto(pass1)).thenReturn(passDto1);

        List<PassDto> result = cartApplicationService.addToCart(idul, emptyPassDtos);

        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(buyerRepository).update(buyer);
        Mockito.verify(buyer, Mockito.never()).addToCart(Mockito.any(Pass.class));
    }
}
