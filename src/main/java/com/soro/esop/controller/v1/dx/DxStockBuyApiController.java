package com.soro.esop.controller.v1.dx;

import com.soro.esop.domain.ErrorResponse;
import com.soro.esop.entity.DxStockBuy;
import com.soro.esop.service.DxStockBuyService;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/dx")
@RequiredArgsConstructor
public class DxStockBuyApiController {

    private final DxStockBuyService dxService;

    @GetMapping("/stockBuyList/fordx")
    public ResponseEntity<List<DxStockBuy>> getData() {
        List<DxStockBuy>  dxEntities =  dxService.findAll();
        return ResponseEntity.ok(dxEntities);
    }

    @GetMapping("/stockBuyList/fordx/{id}")
    public ResponseEntity<DxStockBuy> getDxStockBuy(@PathVariable String id) {
        DxStockBuy dxEntity = dxService.findById(id);
        return ResponseEntity.ok(dxEntity);
    }

    @PostMapping("/stockBuyList/fordx")
    public ResponseEntity<DxStockBuy> createDxStockBuy(@RequestBody DxStockBuy dxEntity) {
        log.debug("Request to create entity: {}", dxEntity);
        DxStockBuy savedEntity = dxService.save(dxEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/stockBuyList/fordx/{id}")
    public ResponseEntity<?> updateDxStockBuy(@PathVariable String id,
                                              @Valid @RequestBody DxStockBuy dxEntity,
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

        DxStockBuy updatedEntity = dxService.update(dxEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/stockBuyList/fordx/{id}")
    public ResponseEntity<Void> deleteDxStockBuy(@PathVariable String id) {
        dxService.delete(id);
        return ResponseEntity.ok().build();
    }
}
