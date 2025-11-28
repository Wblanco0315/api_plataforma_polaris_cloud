package org.wposs.plataforma_polaris_cloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.wposs.plataforma_polaris_cloud.models.auth.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    Optional<UserEntity> findUserEntityById(Long username);
}
