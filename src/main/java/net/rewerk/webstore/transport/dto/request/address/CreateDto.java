package net.rewerk.webstore.transport.dto.request.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDto {
    @NotNull(message = "Address {validation.common.required}")
    @Size(
            min = 6,
            max = 128,
            message = "Address {validation.common.range}"
    )
    private String address;
    @NotNull(message = "City {validation.common.required}")
    @Size(
            min = 3,
            max = 128,
            message = "City {validation.common.range}"
    )
    private String city;
    @NotNull(message = "Region {validation.common.required}")
    @Size(
            min = 3,
            max = 128,
            message = "Region {validation.common.range}"
    )
    private String region;
    @NotNull(message = "Country {validation.common.required}")
    @Size(
            min = 1,
            max = 4,
            message = "Country {validation.common.range}"
    )
    private String country;
    @NotNull(message = "Postal code {validation.common.required}")
    private Integer postalCode;
}
