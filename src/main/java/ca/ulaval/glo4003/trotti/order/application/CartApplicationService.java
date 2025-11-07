package ca.ulaval.glo4003.trotti.order.application;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import java.util.List;

public class CartApplicationService {

    private final BuyerRepository buyerRepository;
    private final PassMapper passMapper;
    private final PassFactory passFactory;

    public CartApplicationService(
            BuyerRepository buyerRepository,
            PassMapper passMapper,
            PassFactory passFactory) {
        this.buyerRepository = buyerRepository;
        this.passMapper = passMapper;
        this.passFactory = passFactory;
    }

    public List<PassDto> getCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public List<PassDto> addToCart(Idul idul, List<PassDto> passListDto) {
        Buyer buyer = buyerRepository.findByIdul(idul);
        List<Pass> passList = createPasses(passListDto);

        for (Pass pass : passList) {
            buyer.addToCart(pass);
        }

        buyerRepository.update(buyer);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public List<PassDto> removeFromCart(Idul idul, PassId passId) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.removeFromCart(passId);
        buyerRepository.update(buyer);

        return buyer.getCart().getPasses().stream().map(passMapper::toDto).toList();
    }

    public void clearCart(Idul idul) {
        Buyer buyer = buyerRepository.findByIdul(idul);

        buyer.clearCart();
        buyerRepository.update(buyer);
    }

    private List<Pass> createPasses(List<PassDto> passListDto) {
        return passListDto.stream()
                .map(passDto -> passFactory.create(passDto.maximumDailyTravelTime(),
                        passDto.session(), passDto.billingFrequency()))
                .toList();
    }
}
