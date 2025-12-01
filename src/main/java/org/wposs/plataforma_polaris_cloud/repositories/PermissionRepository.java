package org.wposs.plataforma_polaris_cloud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.wposs.plataforma_polaris_cloud.models.auth.PermissionEntity;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Integer> {

}
