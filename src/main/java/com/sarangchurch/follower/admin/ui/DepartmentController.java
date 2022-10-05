package com.sarangchurch.follower.admin.ui;

import com.sarangchurch.follower.admin.application.DepartmentService;
import com.sarangchurch.follower.admin.application.dto.AddMemberRequest;
import com.sarangchurch.follower.admin.domain.model.Admin;
import com.sarangchurch.follower.admin.ui.argumentresolver.AuthAdmin;
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
            @AuthAdmin Admin admin,
            @PathVariable Long departmentId,
            @RequestBody @Valid AddMemberRequest request
    ) {
        Long memberId = departmentService.addMember(admin, departmentId, request);
        return ResponseEntity.created(URI.create("/api/admin/department/" + departmentId + "/member/" + memberId))
                .build();
    }
}
