package com.dairyFarm.dairyFarm.Impl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Fetch logged-in user from SecurityContext (Modify based on authentication method)
        return Optional.of("SystemUser"); // Replace with actual user
    }
}
