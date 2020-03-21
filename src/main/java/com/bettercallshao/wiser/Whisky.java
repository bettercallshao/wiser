package com.bettercallshao.wiser;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Whisky {

    @RequestMapping("/")
    public String index() {
        return "Our signature whisky, J.P. Wiser’s Deluxe is an award-winning, full-flavoured Canadian rye whisky.";
    }

}
