package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Role;
import com.itsol.recruit.repository.RoleRepository;
import com.itsol.recruit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl  implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }
}
