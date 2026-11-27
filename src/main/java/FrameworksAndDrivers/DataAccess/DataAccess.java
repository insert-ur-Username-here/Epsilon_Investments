package FrameworksAndDrivers.DataAccess;

import ApplicationBusinessRules.DataAccessInterface;
import Entities.Portfolio;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DataAccess implements DataAccessInterface {
    @Override
    public void save(Portfolio portfolio) {

    }

    @Override
    public void delete(UUID portfolioUUID) {

    }

    @Override
    public Optional<Portfolio> findById(UUID portfolioId) {
        return Optional.empty();
    }

    @Override
    public List<Portfolio> findAll() {
        return List.of();
    }
}
