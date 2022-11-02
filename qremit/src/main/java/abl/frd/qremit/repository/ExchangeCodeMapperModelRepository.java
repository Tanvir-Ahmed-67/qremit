package abl.frd.qremit.repository;

import abl.frd.qremit.model.ExchangeCodeMapperModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeCodeMapperModelRepository extends JpaRepository<ExchangeCodeMapperModel, Integer> {
}