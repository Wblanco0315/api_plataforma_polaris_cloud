package org.wposs.plataforma_polaris_cloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wposs.plataforma_polaris_cloud.models.auth.RoleEntity;
import org.wposs.plataforma_polaris_cloud.models.auth.enums.RolesEnum;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
    RoleEntity findRoleEntityByRoleEnum(RolesEnum roleEnum);
}
