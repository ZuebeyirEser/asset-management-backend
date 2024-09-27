package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.AssignedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedUserRepository extends JpaRepository<AssignedUser,Long> {
}
