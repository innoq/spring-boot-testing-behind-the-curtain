package com.innoq.talk.spring.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class GreetingController {

    private final Greeter greeter;

    public GreetingController(Greeter greeter) {
        this.greeter = greeter;
    }

    @GetMapping
    public Map<String, String> index(@RequestParam(defaultValue = "Spring") String name) {
        var greeting = greeter.greet(name);
        return Map.of(
                "name", name,
                "greeting", greeting);
    }
}
