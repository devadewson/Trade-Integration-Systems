package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.SwiftMtTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftMtTagRepository extends CrudRepository<SwiftMtTag, Long> {
    @Query("select m from SwiftMtTag m where m.SwiftMtMessageId = ?1")
    List<SwiftMtTag> findBySwiftMtMessageId(Long mtMessageId);
}