package com.soro.esop.controller.v1.dx;

import com.soro.esop.domain.ErrorResponse;
import com.soro.esop.entity.DxUser;
import com.soro.esop.service.DxUserService;
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
public class DxUserApiController {

    private final DxUserService dxService;

    @GetMapping("/userList/fordx")
    public ResponseEntity<List<DxUser>> getData() {
        List<DxUser>  dxEntities =  dxService.findAll();
        return ResponseEntity.ok(dxEntities);
    }

    @PostMapping("/userList/fordx")
    public ResponseEntity<DxUser> createDxUser(@RequestBody DxUser dxEntity) {
        DxUser savedEntity = dxService.save(dxEntity);
        return ResponseEntity.ok(savedEntity);
    }

    @PutMapping("/userList/fordx/{id}")
    public ResponseEntity<?> updateDxUser(@PathVariable Long id,
                                          @Valid @RequestBody DxUser dxEntity,
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

        DxUser updatedEntity = dxService.update(dxEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/userList/fordx/{id}")
    public ResponseEntity<Void> deleteDxUser(@PathVariable Long id) {
        dxService.delete(id);
        return ResponseEntity.ok().build();
    }
}
