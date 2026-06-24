package org.soroth.procuctapi.repository;

import org.soroth.procuctapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag ,Long> {
}
