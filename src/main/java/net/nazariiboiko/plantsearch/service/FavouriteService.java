package net.nazariiboiko.plantsearch.service;

public interface FavouriteService {
    void changeLikeStatement(Long plantId, Long id);

    boolean getLikeStatement(Long plantId, Long id);
}
