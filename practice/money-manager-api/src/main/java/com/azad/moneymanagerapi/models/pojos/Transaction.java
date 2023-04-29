package com.azad.moneymanagerapi.models.pojos;

import com.azad.moneymanagerapi.commons.validations.EnumValidator;
import com.azad.moneymanagerapi.models.constants.CategoryTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Transaction {

    @NotNull(message = "Transaction date cannot be empty (dd/MM/yyyy)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected LocalDate date;

    @NotNull(message = "Transaction amount cannot be empty")
    protected Double amount;

    protected String note;

    protected String description;

    @NotNull(message = "Transaction type cannot be empty. Valid values are INCOME/EXPENSE")
    @EnumValidator(enumClass = CategoryTypes.class, message = "Not a valid transaction type")
    protected String transactionType;
}
