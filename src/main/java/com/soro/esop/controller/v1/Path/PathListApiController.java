package com.soro.esop.controller.v1.Path;

import com.soro.esop.domain.ErrorResponse;
import com.soro.esop.entity.PathList;
import com.soro.esop.service.PathListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:48
 * @modify date : 2024-08-17 16:52:48
 * @desc [BoardController.java] 
 */


/**
 * 게시판 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/path")
@RequiredArgsConstructor
public class PathListApiController {

    private final PathListService pathListService;

    @GetMapping("/list")
    public ResponseEntity<List<PathList>> getData() {
        List<PathList>  dxEntities =  pathListService.findAll();
        return ResponseEntity.ok(dxEntities);
    }

    @PostMapping("/list")
    public ResponseEntity<?> createPathList(@Valid @RequestBody PathList dxEntity, BindingResult bindingResult) {
        log.debug("Request to create entity: {}", dxEntity);
        if(bindingResult.hasErrors()) {
            log.error("errors: {}", bindingResult.getAllErrors());

            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse(errors));
        }
        PathList savedEntity = pathListService.save(dxEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<?> updatePathList(@PathVariable Long id,
                                          @Valid @RequestBody PathList dxEntity,
                                          BindingResult bindingResult) {
        dxEntity.setId(id);
        log.debug("Request to update entity: {}", dxEntity);

        if (bindingResult.hasErrors()) {
            log.error("errors: {}", bindingResult.getAllErrors());

            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse(errors));
        }

        PathList updatedEntity = pathListService.update(dxEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<Void> deletePathList(@PathVariable Long id) {
        try{
            pathListService.delete(id);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            log.error("Error deleting entity with id: {}, e:{}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
