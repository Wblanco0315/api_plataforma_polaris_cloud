package org.wposs.plataforma_polaris_cloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.wposs.plataforma_polaris_cloud.models.auth.RoleEntity;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Set<RoleEntity> findRoleEntitiesByNameIn(Collection<String> names);
}
