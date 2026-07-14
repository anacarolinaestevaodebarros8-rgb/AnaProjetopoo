package com.folhear.repository;

import com.folhear.entity.ObraAutoral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ObraAutorialRepository extends JpaRepository<ObraAutoral, UUID> {
}
