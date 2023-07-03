package com.azad.hosteldiningapi.models.memberinfo;

import com.azad.hosteldiningapi.models.PojoModel;
import com.azad.hosteldiningapi.common.utils.ApiUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class MemberInfo extends PojoModel {

    @NotNull(message = "Roll no cannot be empty")
    protected String rollNo;

    @NotNull(message = "Room no cannot be empty")
    protected String roomNo;

    @NotNull(message = "Seat no cannot be empty")
    protected String seatNo;

    @NotNull(message = "Session cannot be empty")
    protected String session;

    @NotNull(message = "Department cannot be empty")
    protected String department;

    protected Long totalTokenReceived;
    protected Double totalExpenses;

    @Override
    public String getUid() {
        return ApiUtils.generateMemberInfoUid(rollNo, department, session);
    }
}
