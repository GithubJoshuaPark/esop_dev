package com.soro.esop.controller.v1.User;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.soro.esop.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soro.esop.entity.Role;
import com.soro.esop.entity.User;
import com.soro.esop.entity.UserRole;
import com.soro.esop.mapper.UserMapper;
import com.soro.esop.model.UserDto;
import com.soro.esop.service.RoleService;
import com.soro.esop.service.UserRoleService;
import com.soro.esop.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//import com.querydsl.core.types.Predicate;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-17 16:52:48
 * @modify date : 2024-08-17 16:52:48
 * @desc [UserController.java]
 */


/**
 * 사용자 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final BCryptPasswordEncoder passwordEncoder; // 패스워드 인코더

    /**
     * 사용자 목록 조회
     * @return
     */
    @SuppressWarnings("deprecation")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll( @RequestParam(required = false, defaultValue = "", name = "method") String method,
                                                 @RequestParam(required = false, defaultValue = "", name="username") String username,
                                                 Pageable pageable)
    {
        log.debug("method: {}", method);
        log.debug("username: {}", username);

        if("query".equals(method)) {
            // for using JPQL
            log.debug("for using JPQL");
            List<User> userList =  userService.findByUsernameQuery(username);
            return ResponseEntity.ok(UserMapper.toDto(userList)); // 200 OK
        }
        else if("native".equals(method)) {
            // for using native query
            log.debug("for using native query");
            List<User> userList =  userService.findByUsernameNativeQuery(username);
            return ResponseEntity.ok(UserMapper.toDto(userList)); // 200 OK
        }
         else if("querydsl".equals(method)) {
             // for using querydsl
             log.debug("for using querydsl");

             QUser user          = QUser.user;
             Predicate predicate = user.username.containsIgnoreCase(username)
	                              .or(user.username.startsWithIgnoreCase("s"));

             Iterable<User> userList =  userService.findAllOfQueryDsl(predicate);
             return ResponseEntity.ok(UserMapper.toDto((List<User>) userList)); // 200 OK
         }
        else if ("entityManager".equals(method)) {
            // for using CustomizedRepository
            log.debug("for using EntityManager");
            List<User> userList =  userService.testOfCustomizedRepository(username);
            return ResponseEntity.ok(UserMapper.toDto(userList)); // 200 OK
        }
        else if("jdbc".equals(pageable)) {
            // for using CustomizedRepositoryJDBC
            log.debug("for using jdbc");
            List<User> userList =  userService.testOfCustomizedRepositoryJDBC(username);
            return ResponseEntity.ok(UserMapper.toDto(userList)); // 200 OK
        }
        else if(StringUtils.isEmpty(username)) {
            Page<User> userList =  userService.findAll(pageable);
            return ResponseEntity.ok(UserMapper.toDto(userList.getContent())); // 200 OK
        }
        else {
            User user =  userService.findByUsername(username);
            UserDto userDto = UserMapper.toDto(user);
            return ResponseEntity.ok(List.of(userDto)); // 200 OK
        }
    }

    /**
     * 사용자 상세 조회
     * @param id
     * @return: ResponseEntity<UserDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable(name="id") Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(UserMapper.toDto(user)); // 200 OK
    }

    /**
     * 사용자 등록
     * @param userDto
     * @return: ResponseEntity<UserDto>
     */
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        // 패스워드 암호화
        String encrptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encrptedPassword);

        User user = UserMapper.toEntity(userDto);

        // 사용자 저장
        user = userService.save(user);

        Role role = roleService.findById(1L);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());

        // 사용자 권한 저장
        userRoleService.save(userRole);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user)); // 201 Created
    }

    /**
     * 사용자 수정
     * @param id
     * @param userDto
     * @return
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> putMethodName(@PathVariable(name="id") String id,
                                                  @RequestBody UserDto userDto)
    {
        // 패스워드 암호화
        String encrptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encrptedPassword);

        User user = userService.findById(Long.parseLong(id));
        if(user == null) {
            // insert
            user = UserMapper.toEntity(userDto);
            user = userService.save(user);

            Role role = roleService.findById(1L);
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());

            // 사용자 권한 저장
            userRoleService.save(userRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user)); // 201 Created
        }
        else {
            // update
            user.setUsername(userDto.getUsername());
            user.setPassword(encrptedPassword);
            user.setEnabled(userDto.getEnabled());
            user = userService.save(user);
            return ResponseEntity.ok(UserMapper.toDto(user)); // 200 OK
        }
    }

    /**
     * 사용자 삭제
     * @param id
     * @return
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name="id") Long id) {
        User user = userService.findById(id);
        if(user == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        Long userId = user.getId();

        // 사용자 권한 삭제
        userRoleService.deleteByUserId(userId);

        // 사용자 삭제
        userService.delete(id);

        // return with message "User deleted successfully"
        return ResponseEntity.ok("User deleted successfully"); // 200 OK

    }
}
