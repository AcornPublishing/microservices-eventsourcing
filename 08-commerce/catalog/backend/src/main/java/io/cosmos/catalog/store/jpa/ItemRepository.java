package io.cosmos.catalog.store.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemJpo, String> {
    //
    List<ItemJpo> findAllByCatalogId(String catalogId);
}
