package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
}
