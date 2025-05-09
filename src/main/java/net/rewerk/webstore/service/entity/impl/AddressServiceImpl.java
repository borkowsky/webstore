package net.rewerk.webstore.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.dto.request.address.CreateDto;
import net.rewerk.webstore.model.dto.request.address.PatchDto;
import net.rewerk.webstore.model.entity.Address;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.model.mapper.AddressDtoMapper;
import net.rewerk.webstore.repository.AddressRepository;
import net.rewerk.webstore.service.entity.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressDtoMapper addressDtoMapper;
    private final AddressRepository addressRepository;

    @Override
    public Address findById(@NonNull Integer id, @NonNull User user) {
        return addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public Page<Address> findAll(Specification<Address> specification, Pageable pageable) {
        return addressRepository.findAll(specification, pageable);
    }

    @Override
    public Address create(@NonNull CreateDto dto, @NonNull User user) {
        Address mapped = addressDtoMapper.createDtoToAddress(dto);
        mapped.setUserId(user.getId());
        return addressRepository.save(mapped);
    }

    @Override
    public Address update(@NonNull Address address,
                          @NonNull PatchDto patchDto,
                          @NonNull User user) {
        if (!Objects.equals(address.getUserId(), user.getId())) {
            throw new EntityNotFoundException("Address not found");
        }
        return addressRepository.save(addressDtoMapper.patchDtoToAddress(address, patchDto));
    }

    @Override
    public void delete(@NonNull Address address, @NonNull User user) {
        if (!Objects.equals(address.getUserId(), user.getId())) {
            throw new EntityNotFoundException("Address not found");
        }
        addressRepository.delete(address);
    }
}
