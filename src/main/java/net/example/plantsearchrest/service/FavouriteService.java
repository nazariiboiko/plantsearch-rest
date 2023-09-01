package net.example.plantsearchrest.service;

import net.example.plantsearchrest.exception.NotFoundException;

public interface FavouriteService {
    void changeLikeStatement(Long plantId, Long userId) throws NotFoundException;
}
