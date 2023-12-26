package com.azad.supershop.model.pojo;

import com.azad.supershop.model.constant.ProductUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class SupplyContract {

    @NotNull(message = "Quantity cannot be null")
    protected Integer quantity;

    @NotNull(message = "Product unit cannot be null")
    @Enumerated(EnumType.STRING)
    protected ProductUnit unit;

    @NotNull(message = "Total cost cannot be null")
    protected Double totalCost;

    @NotNull(message = "Contract date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected LocalDate contractDate;
}
