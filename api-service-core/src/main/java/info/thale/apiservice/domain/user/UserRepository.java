package info.thale.apiservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import info.thale.apiservice.domain.user.model.User;
import info.thale.apiservice.domain.UserId;

@Repository
public interface UserRepository extends MongoRepository<User, UserId> {

    User findUserByEmail(String email);

}
