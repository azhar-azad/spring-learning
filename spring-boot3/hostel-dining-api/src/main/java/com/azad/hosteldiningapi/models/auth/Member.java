package com.azad.hosteldiningapi.models.auth;

import com.azad.hosteldiningapi.models.PojoModel;
import com.azad.hosteldiningapi.common.utils.ApiUtils;
import com.azad.hosteldiningapi.models.memberinfo.MemberInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Member extends PojoModel {

    @NotNull(message = "First name cannot be empty.")
    @Size(min = 1, max = 50, message = "First name length has to be between 1 to 50 characters.")
    protected String firstName;

    @NotNull(message = "Last name cannot be empty.")
    @Size(min = 1, max = 50, message = "Last name length has to be between 1 to 50 characters.")
    protected String lastName;

    @NotNull(message = "Email cannot be empty.")
    @Email(message = "Please provide a valid email",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    protected String email;

    @NotNull(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime lastLoginAt;

    protected MemberInfo memberInfo;

    @Override
    public String getUid() {
        return ApiUtils.generateMemberUid(email, firstName, lastName);
    }
}
