package tech.lancelot.repository.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.lancelot.domain.blog.Archive;
import tech.lancelot.domain.blog.Tag;

/**
 * @author lancelot
 */
public interface ArchiveRepository extends JpaRepository<Archive, String>, JpaSpecificationExecutor<Archive> {

}
