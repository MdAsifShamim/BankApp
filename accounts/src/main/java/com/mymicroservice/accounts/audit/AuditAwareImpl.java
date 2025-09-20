package com.mymicroservice.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("AuditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    /**
     * Return the current Auditor of the application
     * @return the current Auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Accounts_MS");
    }
}
