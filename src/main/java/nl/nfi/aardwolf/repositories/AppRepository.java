/*
 * Copyright (c) 2021, Netherlands Forensic Institute
 * All rights reserved.
 */
package nl.nfi.aardwolf.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.nfi.aardwolf.domain.App;
import nl.nfi.aardwolf.domain.Marketplace;

public interface AppRepository extends CrudRepository<App, Long> {
    @Override
    List<App> findAll();

    Collection<App> findByNameLikeIgnoreCaseOrVersionLikeIgnoreCaseOrPublisherLikeIgnoreCaseOrDescriptionLikeIgnoreCase(String name, String version, String publisher, String description);

    boolean existsByNameAndVersionAndMarketplace(String name, String version, Marketplace playstore);
}