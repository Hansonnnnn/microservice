package cn.edu.nju.user.dao;

import entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, BatchDao<User> {
    List<User> findAll();

    User findByUsername(String username);

    User findByMobile(String mobile);
}
