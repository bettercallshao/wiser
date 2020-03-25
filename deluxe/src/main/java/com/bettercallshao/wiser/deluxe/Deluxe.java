package com.bettercallshao.wiser.deluxe;

import com.bettercallshao.wiser.spice.Item;
import com.bettercallshao.wiser.spice.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@ComponentScan("com.bettercallshao.wiser.spice")
@EntityScan("com.bettercallshao.wiser.spice")
@EnableJpaRepositories("com.bettercallshao.wiser.spice")
public class Deluxe {

    @Value("${deluxe.pagesize:12}")
    private Integer pagesize;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0", required = false) Integer page,
                        Model model) {
        List<Item> list = itemRepository.findAllByOrderByTimestampDesc(
                PageRequest.of(page, pagesize)
        );
        model.addAttribute("items", list);
        model.addAttribute("next", page + 1);
        return "index";
    }

}
