package chat.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chat.chat.model.entity.ChatEntity;

@Repository
public interface IChatRepository extends MongoRepository<ChatEntity, String> {
}
