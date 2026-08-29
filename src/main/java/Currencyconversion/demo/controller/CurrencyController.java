package Currencyconversion.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Currencyconversion.demo.service.CurrencyConversionService;

import java.math.BigDecimal;

@Tag(name = "Currency Conversion", description = "APIs for converting between currencies using live exchange rates")
@RestController
@RequestMapping("/api/currency")
@CrossOrigin("*")
public class CurrencyController {

    @Autowired
    private CurrencyConversionService service;

    @RequestMapping("/hi")
    public String getSayhi()
    {
       return "Mahamani" ;
    }

    @Operation(
        summary = "Convert currency",
        description = "Converts a given amount from one currency to another using live rates from CurrencyBeacon. " +
                      "Rates are cached in the database for 1 hour to reduce external API calls."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully converted the currency amount"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error or upstream API failure")
    })
    @GetMapping("/convert")
    public BigDecimal convert(
            @Parameter(description = "Source currency code (e.g. USD, EUR, GBP)", example = "USD", required = true)
            @RequestParam String source,

            @Parameter(description = "Target currency code (e.g. INR, JPY, AUD)", example = "INR", required = true)
            @RequestParam String target,

            @Parameter(description = "Amount to convert", example = "100", required = true)
            @RequestParam Integer amount) {

        return service.convert(source, target, amount);
    }
}
