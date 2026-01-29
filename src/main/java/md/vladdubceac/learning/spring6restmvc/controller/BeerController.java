package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;
import md.vladdubceac.learning.spring6restmvc.model.BeerStyle;
import md.vladdubceac.learning.spring6restmvc.services.BeerService;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity patchById(@PathVariable(ID_VARIABLE) UUID id, @RequestBody BeerDTO beer) {
        if (beerService.patchById(id, beer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity delete(@PathVariable(ID_VARIABLE) UUID id) {
        boolean deleted = beerService.delete(id);
        if (!deleted) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity updateById(@PathVariable(ID_VARIABLE) UUID id, @Validated @RequestBody BeerDTO beer) {
        if (beerService.updateById(id, beer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer) {
        BeerDTO beerSaved = beerService.saveNewBeer(beer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", PATH + beerSaved.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(PATH)
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName, @RequestParam(required = false) BeerStyle beerStyle, @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {
        return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }

    @GetMapping(PATH_ID)
    public BeerDTO getBeerById(@PathVariable(ID_VARIABLE) UUID beerId) {

        log.debug("Get Beer by ID - in container");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }
}
