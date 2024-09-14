package com.soro.esop.controller.v1.dx;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;
import com.soro.esop.service.DxUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<DxUser> updateDxUser(@PathVariable Long id, @RequestBody DxUser dxEntity) {
        dxEntity.setId(id);
        DxUser updatedEntity = dxService.update(dxEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/userList/fordx/{id}")
    public ResponseEntity<Void> deleteDxUser(@PathVariable Long id) {
        dxService.delete(id);
        return ResponseEntity.ok().build();
    }
}
