package com.azad.moneymanagerapi.models.pojos;

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
public class Transfer {

    @NotNull(message = "Transfer date cannot be empty (dd/MM/yyyy)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    protected LocalDate date;

    @NotNull(message = "Transfer from which account? Please provide the account name")
    protected String fromAccount;

    @NotNull(message = "Transfer to which account? Please provide the account name")
    protected String toAccount;

    protected Double transferFees;

    @NotNull(message = "Transfer amount cannot be empty")
    protected Double amount;

    protected String note;

    protected String description;
}
