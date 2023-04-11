package constsw.grupoum.oauth.application.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import constsw.grupoum.oauth.application.record.ResponseUser;
import constsw.grupoum.oauth.integration.keycloak.record.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ResponseUser userToResponseUser(User user);

    Collection<ResponseUser> collectionUsertoCollectionResponseUsers(Collection<User> users);

}
