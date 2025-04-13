package com.bcaf.bcapay.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcaf.bcapay.dto.FeatureDto;
import com.bcaf.bcapay.dto.ResponseDto;
import com.bcaf.bcapay.models.RoleFeature;
import com.bcaf.bcapay.services.RoleFeatureService;

@RestController
@RequestMapping("api/v1/role-features")
public class RoleFeatureController {
    @Autowired
    private RoleFeatureService roleFeatureService;

    @Secured("FEATURE_MANAGE_ROLE_FEATURES")
    @GetMapping
    public ResponseEntity<ResponseDto> getAllRoles() {
        List<RoleFeature> roleFeatures = roleFeatureService.getAlRoleFeatures();
        return ResponseEntity
                .ok(new ResponseDto(200, "success", roleFeatures.size() + " role features found", roleFeatures));
    }

    @Secured("FEATURE_MANAGE_ROLE_FEATURES")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getRoleFeatureById(@PathVariable String id) {
        RoleFeature roleFeature = roleFeatureService.getRoleFeatureById(id);
        return ResponseEntity.ok(new ResponseDto(200, "success", "Role Feature found", roleFeature));
    }

    @Secured("FEATURE_MANAGE_ROLE_FEATURES")
    @PostMapping
    public ResponseEntity<ResponseDto> createRoleFeature(@RequestBody Map<String, Object> payload) {

        RoleFeature createdRoleFeature = roleFeatureService.createRoleFeature(payload);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "success", "Role Feature assigned", createdRoleFeature));
    }

    @Secured("FEATURE_MANAGE_ROLE_FEATURES")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRoleFeatureById(@PathVariable String id) {
        roleFeatureService.deleteRoleFeature(id);
        return ResponseEntity.ok(new ResponseDto(200, "success", "Role Feature deleted", null));
    }

    @Secured("FEATURE_MANAGE_ROLE_FEATURES")
    @GetMapping("/role/{roleId}/features")
    public ResponseEntity<ResponseDto> getFeaturesByRoleId(@PathVariable String roleId) {
        List<FeatureDto> features = roleFeatureService.getFeaturesByRoleId(UUID.fromString(roleId));
        return ResponseEntity.ok(new ResponseDto(200, "success", features.size() + " features found", features));
    }

}
