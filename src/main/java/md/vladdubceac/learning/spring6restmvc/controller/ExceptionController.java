package md.vladdubceac.learning.spring6restmvc.controller;

import lombok.extern.slf4j.Slf4j;
import md.vladdubceac.learning.spring6restmvc.utils.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        log.error("Handling NotFoundException ... ");
        return ResponseEntity.notFound().build();
    }
}
