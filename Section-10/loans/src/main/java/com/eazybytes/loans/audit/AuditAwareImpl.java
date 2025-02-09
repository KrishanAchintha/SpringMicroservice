package com.eazybytes.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl  implements AuditorAware<String> {
         /**
         * This method is used to get the current auditor of the application
         * @return the current auditor
         */
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of("Loans_MS");
        }
}
