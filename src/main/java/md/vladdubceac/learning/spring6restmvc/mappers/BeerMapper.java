package md.vladdubceac.learning.spring6restmvc.mappers;

import md.vladdubceac.learning.spring6restmvc.entities.Beer;
import md.vladdubceac.learning.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDTO(Beer beer);
}
