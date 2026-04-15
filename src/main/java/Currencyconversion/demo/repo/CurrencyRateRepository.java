package Currencyconversion.demo.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Currencyconversion.demo.model.CurrencyRate;


@Repository
public interface CurrencyRateRepository
extends JpaRepository<CurrencyRate, Long> {

Optional<CurrencyRate> findBySourceCurrencyAndTargetCurrency(
    String sourceCurrency, String targetCurrency);
}
