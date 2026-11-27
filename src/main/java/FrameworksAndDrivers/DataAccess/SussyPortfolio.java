package FrameworksAndDrivers.DataAccess;

import Entities.Portfolio;
import ApplicationBusinessRules.DataAccessInterface.PortfolioDataAccessInterface;

import java.math.BigDecimal;

public class SussyPortfolio implements PortfolioDataAccessInterface {

    private Portfolio sussyPortfolio = new Portfolio("sussy portfolio", BigDecimal.valueOf(9999));

    @Override
    public Portfolio getPortfolio() {
        return sussyPortfolio;
    }

    @Override
    public void setPortfolio(Portfolio sussierPortfolio) {
        this.sussyPortfolio = sussierPortfolio;
    }
}
