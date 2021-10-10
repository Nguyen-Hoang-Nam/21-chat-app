package chat.chat.repository;

import chat.chat.model.entity.ChatEntity;
import chat.chat.model.entity.MessageEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRepository extends MongoRepository<ChatEntity, String> {
    // Credit: https://stackoverflow.com/questions/37195296/spring-mongodb-pagination-of-nested-collection-field
    @Query(value = "{id: ?0}", fields = "{messageEntities: {$slice: [?1, ?2]}}")
    List<MessageEntity> findMessages(String id, int skip, int limit);
}
