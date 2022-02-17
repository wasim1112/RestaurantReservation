package app.general.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import app.iam.user.domain.User;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig  {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new EntityAuditorAware();
    }

    static class EntityAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)) {
                return Optional.empty();
            }else{
                return Optional.of(((User) authentication.getPrincipal()).getId().toString());
            }
        }
    }

}
