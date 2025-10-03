package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.api.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.resources.AccountResource;
import ca.ulaval.glo4003.trotti.api.resources.AuthenticationResource;
import ca.ulaval.glo4003.trotti.api.resources.CartResource;
import ca.ulaval.glo4003.trotti.api.resources.OrderResource;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceLocator locator = ServerResourceLocator.getInstance();
        bind(locator.resolve(AuthenticationService.class)).to(AuthenticationService.class);
        bind(locator.resolve(EmailService.class)).to(EmailService.class);
        bind(locator.resolve(PasswordHasher.class)).to(PasswordHasher.class);
        bind(locator.resolve(RidePermitHistoryGateway.class)).to(RidePermitHistoryGateway.class);
        bind(locator.resolve(RidePermitActivationApplicationService.class))
                .to(RidePermitActivationApplicationService.class);

        bind(locator.resolve(AccountRepository.class)).to(AccountRepository.class);
        bind(locator.resolve(PassRepository.class)).to(PassRepository.class);
        bind(locator.resolve(BuyerRepository.class)).to(BuyerRepository.class);

        bind(locator.resolve(AccountApplicationService.class)).to(AccountApplicationService.class);

        bind(locator.resolve(AccountApiMapper.class)).to(AccountApiMapper.class);

        bind(locator.resolve(AccountResource.class)).to(AccountResource.class);
        bind(locator.resolve(AuthenticationResource.class)).to(AuthenticationResource.class);
        bind(locator.resolve(CartResource.class)).to(CartResource.class);
        bind(locator.resolve(OrderResource.class)).to(OrderResource.class);
    }
}
