package com.soro.esop.controller.v1.dx;

import com.soro.esop.domain.ErrorResponse;
import com.soro.esop.entity.DxEntity;
import com.soro.esop.service.DxEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/dx")
@RequiredArgsConstructor
public class DxEntityApiController {

    private final DxEntityService dxService;

    @GetMapping("/entityList/fordx")
    public ResponseEntity<List<DxEntity>> getData() {
        List<DxEntity>  dxEntities = dxService.findAll();
        return ResponseEntity.ok(dxEntities);
    }

    @PostMapping("/entityList/fordx")
    public ResponseEntity<DxEntity> createDxEntity(@RequestBody DxEntity dxEntity) {
        log.debug("Request to create entity: {}", dxEntity);
        DxEntity savedEntity = dxService.save(dxEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/entityList/fordx/{id}")
    public ResponseEntity<?> updateDxEntity(@PathVariable(name="id") String id,
                                            @Valid @RequestBody DxEntity dxEntity,
                                            BindingResult bindingResult)
    {
        dxEntity.setId(id);
        log.debug("Request to update entity: {}", dxEntity);

        if (bindingResult.hasErrors()) {
            log.error("errors: {}", bindingResult.getAllErrors());

            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse(errors));
        }

        DxEntity updatedEntity = dxService.update(dxEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/entityList/fordx/{id}")
    public ResponseEntity<Void> deleteDxEntity(@PathVariable(name="id") String id) {
        dxService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    
}
