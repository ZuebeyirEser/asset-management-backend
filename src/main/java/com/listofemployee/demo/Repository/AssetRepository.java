package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.Asset;
import com.listofemployee.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {

    List<Asset> findByUser(Optional<User> user);
}
