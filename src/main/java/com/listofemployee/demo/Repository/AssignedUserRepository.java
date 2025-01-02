package com.listofemployee.demo.Repository;

import com.listofemployee.demo.Model.AssignedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface extends JpaRepository and provides basic CRUD operations for AssignedUser entities.
 * It is annotated with @Repository to indicate it's a repository layer component.
 */
@Repository
public interface AssignedUserRepository extends JpaRepository<AssignedUser,Long> {
}
