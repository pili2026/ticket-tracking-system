package com.ticket.tracking.service;

import com.ticket.tracking.convert.RoleConverter;
import com.ticket.tracking.convert.TicketConverter;
import com.ticket.tracking.entity.*;
import com.ticket.tracking.parameter.RoleQueryParameter;
import com.ticket.tracking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleResponse> getRoleResponses(RoleQueryParameter param) {
        String nameKeyword = Optional.ofNullable(param.getKeyword()).orElse("");
        int dateFrom = Optional.ofNullable(param.getCreateDateFrom()).orElse(0);
        int dateTo = Optional.ofNullable(param.getCreateDateTo()).orElse(Integer.MAX_VALUE);
        Sort sort = configureSort(param.getOrderBy(), param.getSortRule());

        List<Role> roles = roleRepository.findByCreateDateToBetweenAndSummaryLikeIgnoreCase(dateFrom, dateTo, nameKeyword, sort);
        return roles.stream()
                .map(RoleConverter::toRoleResponse)
                .collect(Collectors.toList());
    }

    // get same role
    public List<RoleResponse> getRoleResponsesByType(String roleType) {
        List<Role> roles = roleRepository.findByRoleTypeLikeIgnoreCase(roleType);

        return roles.stream()
                .map(RoleConverter::toRoleResponse)
                .collect(Collectors.toList());
    }

    // get role with specified type and ID
    public RoleResponse getRoleResponsesByTypeId(String roleType, String id) {
        Role roles = roleRepository.findByRoleTypeByIdLikeIgnoreCase(roleType, id);
        return RoleConverter.toRoleResponse(roles);
    }

    public RoleResponse createRole(RoleRequest request) {
        Role role = roleObj(request);
        roleRepository.insert(role);
        return RoleConverter.toRoleResponse(role);
    }

    private Sort configureSort(String orderBy, String sortRule) {
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
            Sort.Direction direction = Sort.Direction.fromString(sortRule);
            sort = new Sort(direction, orderBy);
        }

        return sort;
    }

    /*  ----with role object----  */
    private Role roleObj(RoleRequest request) {
        Role role = new Role();
        role.setAccount(request.getAccount());
        role.setPassword(request.getPassword());
        role.setRoleType(request.getRoleType());
        role.setAuthority(request.getAuthority());

        return role;
    }
}
