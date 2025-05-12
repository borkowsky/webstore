package net.rewerk.webstore.transport.dto.request.address;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchDto {
    @Size(
            min = 6,
            max = 128,
            message = "Address {validation.common.range}"
    )
    private String address;
    @Size(
            min = 3,
            max = 128,
            message = "City {validation.common.range}"
    )
    private String city;
    @Size(
            min = 3,
            max = 128,
            message = "Region {validation.common.range}"
    )
    private String region;
    @Size(
            min = 1,
            max = 4,
            message = "Country {validation.common.range}"
    )
    private String country;
    private Integer postalCode;
}
