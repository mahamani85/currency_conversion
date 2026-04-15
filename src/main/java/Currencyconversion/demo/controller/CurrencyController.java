package Currencyconversion.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import Currencyconversion.demo.service.CurrencyConversionService;

import java.math.BigDecimal;




@RestController
@RequestMapping("/api/currency")
@CrossOrigin("*")
public class CurrencyController {

    @Autowired
    private CurrencyConversionService service;

    @GetMapping("/convert")
    public BigDecimal convert(
            @RequestParam String source,
            @RequestParam String target,
            @RequestParam Integer amount) {

        return service.convert(source, target, amount);
    }
}

