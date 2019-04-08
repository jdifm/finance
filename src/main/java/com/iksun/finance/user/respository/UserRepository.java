package com.iksun.finance.user.respository;

import com.iksun.finance.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findFirstByTokenIs(String token);
}
