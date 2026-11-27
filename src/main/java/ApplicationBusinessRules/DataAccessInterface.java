package ApplicationBusinessRules;

import Entities.Portfolio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataAccessInterface {
    void save(Portfolio portfolio);
    void delete(UUID portfolioUUID);
    Optional<Portfolio> findById(UUID portfolioId);
    List<Portfolio> findAll();
}