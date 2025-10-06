package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.vladdubceac.learning.spring6restmvc.model.Beer;
import md.vladdubceac.learning.spring6restmvc.services.BeerService;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String PATH = "/api/v1/beer/";
    public static final String ID_VARIABLE = "id";
    public static final String PATH_ID = PATH + "{" + ID_VARIABLE + "}";
    private final BeerService beerService;

    @PatchMapping(PATH_ID)
    public ResponseEntity patchById(@PathVariable(ID_VARIABLE) UUID id, @RequestBody Beer beer) {
        beerService.patchById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity delete(@PathVariable(ID_VARIABLE) UUID id) {
        beerService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity updateById(@PathVariable(ID_VARIABLE) UUID id, @RequestBody Beer beer) {
        beerService.updateById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer beerSaved = beerService.saveNewBeer(beer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", PATH + beerSaved.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(PATH_ID)
    public Beer getBeerById(@PathVariable(ID_VARIABLE) UUID beerId) {

        log.debug("Get Beer by ID - in container");

        return beerService.getBeerById(beerId);
    }
}
