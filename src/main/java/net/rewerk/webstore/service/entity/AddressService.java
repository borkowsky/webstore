package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.transport.dto.request.address.CreateDto;
import net.rewerk.webstore.transport.dto.request.address.PatchDto;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AddressService {
    Address findById(Integer id, User user);

    Page<Address> findAll(Specification<Address> specification, Pageable pageable);

    List<Address> findByUser(User user);

    Address create(CreateDto dto, User user);

    Address update(Address address, PatchDto dto, User user);

    void delete(Address address, User user);
}
