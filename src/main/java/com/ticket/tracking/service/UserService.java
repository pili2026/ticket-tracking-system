package com.ticket.tracking.service;

import com.ticket.tracking.convert.UserConverter;
import com.ticket.tracking.entity.user.User;
import com.ticket.tracking.entity.user.UserRequest;
import com.ticket.tracking.entity.user.UserResponse;
import com.ticket.tracking.parameter.UserQueryParameter;
import com.ticket.tracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getUserResponses(UserQueryParameter param) {
        String nameKeyword = Optional.ofNullable(param.getKeyword()).orElse("");
        int dateFrom = Optional.ofNullable(param.getCreateDateFrom()).orElse(0);
        int dateTo = Optional.ofNullable(param.getCreateDateTo()).orElse(Integer.MAX_VALUE);
        Sort sort = configureSort(param.getOrderBy(), param.getSortRule());

        List<User> users = userRepository.findByCreateDateToBetweenAndUserTypeLikeIgnoreCase(dateFrom, dateTo, nameKeyword, sort);
        return users.stream()
                .map(UserConverter::toUserResponse)
                .collect(Collectors.toList());
    }

    // get same role
    public List<UserResponse> getUserResponsesByType(String userType) {
        List<User> users = userRepository.findByUserTypeLikeIgnoreCase(userType);

        return users.stream()
                .map(UserConverter::toUserResponse)
                .collect(Collectors.toList());
    }

    // get role with specified type and ID
    public UserResponse getUserResponsesByTypeId(String userType, String account) {
        User users = userRepository.findByUserTypeByAccountLikeIgnoreCase(userType, account);
        return UserConverter.toUserResponse(users);
    }

    public UserResponse createUser(UserRequest request) {
        User user = userObj(request);
        userRepository.insert(user);
        return UserConverter.toUserResponse(user);
    }

    // Updated role information by account not all
    public UserResponse replaceUserTypeByAccount(String userType, String account, UserRequest request) {
        User oldUser = userRepository.findByUserTypeByAccountLikeIgnoreCase(userType, account);
        User newUser = UserConverter.toUser(request);
        newUser.setId(oldUser.getId());
        userRepository.save(newUser);

        return  UserConverter.toUserResponse(newUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
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
    private User userObj(UserRequest request) {
        User user = new User();
        user.setAccount(request.getAccount());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());
        user.setAuthority(request.getAuthority());

        return user;
    }
}
