package yousang.backend.rest.repository.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yousang.backend.rest.entity.User.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}