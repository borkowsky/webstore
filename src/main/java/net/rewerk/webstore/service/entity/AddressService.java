package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.dto.request.address.CreateDto;
import net.rewerk.webstore.model.dto.request.address.PatchDto;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AddressService {
    Address findById(Integer id, User user);

    Page<Address> findAll(Specification<Address> specification, Pageable pageable);

    Address create(CreateDto dto, User user);

    Address update(Address address, PatchDto dto, User user);

    void delete(Address address, User user);
}
