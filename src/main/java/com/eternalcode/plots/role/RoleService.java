package com.eternalcode.plots.role;

import com.eternalcode.plots.member.Member;

import java.util.Set;

public class RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, RolePermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean hasPermission(Member member, RolePermission... permissions) {
        Role role = this.getRole(member.role());

        for (RolePermission permission : permissions) {
            if (!this.permissionRepository.hasRolePermission(role, permission)) {
                return false;
            }
        }

        return true;
    }

    public Set<RolePermission> getPermissions(Member member) {
        Role role = this.getRole(member.role());

        return this.getPermissions(role);
    }

    private Role getRole(String name) {
        return this.roleRepository.findRoleByName(name).orElse(Role.NONE);
    }

    private Set<RolePermission> getPermissions(Role role) {
        return this.permissionRepository.findAllByRank(role);
    }

}
