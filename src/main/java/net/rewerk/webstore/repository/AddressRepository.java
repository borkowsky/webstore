package net.rewerk.webstore.repository;

import net.rewerk.webstore.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
    Optional<Address> findByIdAndUserId(Integer id, Integer userId);

    List<Address> findByUserId(Integer userId);
}
