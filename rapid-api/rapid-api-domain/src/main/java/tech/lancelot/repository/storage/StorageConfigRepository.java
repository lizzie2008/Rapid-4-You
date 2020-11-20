package tech.lancelot.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tech.lancelot.domain.blog.Archive;
import tech.lancelot.domain.tools.StorageConfig;

/**
 * @author lancelot
 */
@Repository
public interface StorageConfigRepository extends JpaRepository<StorageConfig, String>, JpaSpecificationExecutor<StorageConfig> {

}
