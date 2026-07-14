package com.folhear.repository;

import com.folhear.entity.Remessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemessaRepository extends JpaRepository<Remessa, Long> {
}
