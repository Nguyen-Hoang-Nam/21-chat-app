package chat.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chat.chat.model.entity.UserEntity;

@Repository
public interface IUserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findUserByUsername(String username);

    UserEntity findUserById(String id);
}
