package com.supergose;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope
@Controller
@RequestMapping("/")
public class HelloWorldController {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    private static final String template = "Hello, %s!";
    @Value("${message:Hello default}")
    private String message;
    
    private final AtomicLong counter = new AtomicLong();
    
    private final BookRepository bookRepository;
    
    public HelloWorldController (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        return new Greeting(counter.incrementAndGet(), this.message);
    }
    
    @RequestMapping(path="/cachesample", method=RequestMethod.GET)
    public @ResponseBody Greeting CacheableSample() {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        return new Greeting(counter.incrementAndGet(), "Cacheable example!");
    }

}