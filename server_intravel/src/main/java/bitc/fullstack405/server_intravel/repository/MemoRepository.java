package bitc.fullstack405.server_intravel.repository;

import bitc.fullstack405.server_intravel.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, Long> {

  List<MemoEntity> findBytId(Long tId);

  MemoEntity findBymId(Long mId);

  void deleteBymId(Long mId);
}