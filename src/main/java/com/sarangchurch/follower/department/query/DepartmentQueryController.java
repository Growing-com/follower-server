package com.sarangchurch.follower.department.query;

import com.sarangchurch.follower.common.types.dto.ApiResponse;
import com.sarangchurch.follower.department.query.dto.DepartmentResponse;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.ui.argumentresolver.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepartmentQueryController {

    private final DepartmentDao departmentDao;

    @GetMapping("/api/departments/my")
    public ApiResponse<DepartmentResponse> findMyDepartment(@AuthMember Member member) {
        return ApiResponse.of(departmentDao.findById(member.getDepartmentId()));
    }
}
