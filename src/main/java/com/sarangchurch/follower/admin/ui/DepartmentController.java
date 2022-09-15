package com.sarangchurch.follower.admin.ui;

import com.sarangchurch.follower.admin.application.DepartmentService;
import com.sarangchurch.follower.admin.application.dto.AddMemberRequest;
import com.sarangchurch.follower.member.ui.AuthMember;
import com.sarangchurch.follower.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/admin/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/{departmentId}/member")
    public ResponseEntity<Void> addMember(
            @PathVariable Long departmentId,
            @RequestBody @Valid AddMemberRequest request,
            @AuthMember Member admin
    ) {
        Long memberId = departmentService.addMember(admin, departmentId, request);
        return ResponseEntity.created(URI.create("/api/admin/department/" + departmentId + "/member/" + memberId))
                .build();
    }
}
