package cz.tul.stin.paveltyl.quotes2.controller;

import cz.tul.stin.paveltyl.quotes2.model.Quote;
import cz.tul.stin.paveltyl.quotes2.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) { this.quoteService = quoteService; }

    @GetMapping
    public List<Quote> getQuotes() { return quoteService.getQuotes(); }

    @PostMapping
    public Quote saveQuote(@RequestBody Quote quote) { return quoteService.saveQuote(quote); }

    @GetMapping("/{id}")
    public Quote getQuote(@PathVariable Long id) { return quoteService.getQuote(id); }

    @GetMapping("/random")
    public Quote getRandomQuote() { return quoteService.getRandomQuote(); }

    @PostMapping("/random")
    public Quote saveRandomQuote() { return quoteService.saveRandomQuote(); }
}
