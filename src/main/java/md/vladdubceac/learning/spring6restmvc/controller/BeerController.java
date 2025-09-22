package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import md.vladdubceac.learning.spring6restmvc.services.BeerService;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;
}
