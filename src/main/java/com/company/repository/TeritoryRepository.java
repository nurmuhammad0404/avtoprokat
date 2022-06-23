package com.company.repository;

import com.company.entity.TeritoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TeritoryRepository extends JpaRepository<TeritoryEntity, Integer> {

    Optional<TeritoryEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update TeritoryEntity set name = :name where id = :id")
    int update(@Param("name") String name, @Param("id") Integer id);

        @Transactional
        @Modifying
        @Query("update TeritoryEntity set visible = :visible where id = :id")
        int delete(@Param("visible") boolean b, @Param("id") Integer id);

}
