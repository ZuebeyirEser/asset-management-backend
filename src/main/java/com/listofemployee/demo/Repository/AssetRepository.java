package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.Asset;
import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface extends JpaRepository and provides additional functionality for querying Asset entities.
 * It is annotated with @Repository to indicate it's a repository layer component.
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {

    /**
     * Finds all assets associated with a given user.
     *
     * @param user an optional User object
     * @return a list of Asset entities or an empty list if no assets are found for the user
     */
    List<Asset> findByUser(Optional<User> user);
}
