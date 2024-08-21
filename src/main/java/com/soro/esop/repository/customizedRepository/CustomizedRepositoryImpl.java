package com.soro.esop.repository.customizedRepository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.soro.esop.entiry.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomizedRepositoryImpl implements CustomizedRepository {

    //@PersistenceContext
    //private EntityManager em;

    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> testOfCustomizedRepository(String username) {
        
        // select * from user where username like '%a%' using entity manager,
        return em
              .createQuery("select u from User u where u.username like :username", User.class)
              .setParameter("username", "%" + username + "%")
              .getResultList();
    }

    @Override
    public List<User> testOfCustomizedRepositoryJDBC(String username) {
        // select * from user where username like '%a%' using jdbc template
        String sql = "select * from user where username like ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        };
        
        return jdbcTemplate.query(sql, rowMapper, "%" + username + "%");
    }
    
}
