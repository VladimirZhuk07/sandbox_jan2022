package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.RoleRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDServiceImpl<User> implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final RoleRepository roleRepository;

    @Override
    public UserDto save(UserDto dto) {
        User user = mapper.toEntity(dto);
        user.setTelegramAuthorizationCode(UUID.randomUUID().toString());
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDto update(UserDto dto, long id) {
        User user = repository.findById(id).orElse(null);
        if(user == null)
            return null;
        checkAndSet(user,dto);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public void checkAndSet(User user, UserDto userDto) {
        if(userDto.getFirstName() != null && !user.getFirstName().equals(userDto.getFirstName()) && !userDto.getFirstName().equals("string")){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null && !user.getLastName().equals(userDto.getLastName()) && !userDto.getLastName().equals("string")){
            user.setLastName(userDto.getLastName());
        }
        if(userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail()) && !userDto.getEmail().equals("string")){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPhoneNumber() != null && !user.getPhoneNumber().equals(userDto.getPhoneNumber()) && !userDto.getPhoneNumber().equals("string")){
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if(userDto.getIsFired() != null && user.getIsFired() != userDto.getIsFired()){
            user.setIsFired(userDto.getIsFired());
        }
        if(userDto.getPassword() != null && !user.getPassword().equals(userDto.getPassword()) && !userDto.getPassword().equals("string")){
            user.setPassword(userDto.getPassword());
        }
    }

    @Override
    public Set<Role> getRoles(User user) {
        return user.getRoles();
    }

    @Override
    public void assignUserRole(Long userId, Long roleId) {
        User user = repository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        assert user != null;
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        repository.save(user);
    }

    @Override
    public void unassignUserRole(Long userId, Long roleId) {
        User user = repository.findById(userId).orElse(null);
        assert user != null;
        user.getRoles().removeIf(x -> x.getId().equals(roleId));
        repository.save(user);
    }

    @Override
    public Optional<User> findInvitedUserByAuthorizationCode(String code) {
        return repository.findByTelegramAuthorizationCodeAndStatus(code,UserState.INVITED);
    }

    @Override
    public Optional<User> findUserByChatIdOrPhoneNumber(String chatId, String phone) {
        return repository.findByChatIdOrPhoneNumber(chatId, phone);
    }

    @Override
    public List<User> findAllByStatus(UserState state) {
        return new ArrayList<>(repository.findAllByStatus(state));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public Optional<User> findActiveUserByChatId(String chatId) {
        return repository.findByChatIdAndStatus(chatId, UserState.ACTIVE);
    }

    @Override
    public List<ReportOnUsersDto> getDataForReportOnUsers(Date userBookDateFrom, Date userBookDateTo) {
        return repository.getDataForUsersReport(userBookDateFrom, userBookDateTo);
    }

    @Override
    public List<ReportOnEmployeesDto> getDataForEmployeesReport(Date userCreateDateFrom, Date userCreateDateTo) {
        return repository.getDataForEmployeesReport(userCreateDateFrom, userCreateDateTo);
    }
    @Override
    public List<User> findByIsFiredTrue() {
        return repository.findAllByIsFiredTrueAndStatusNotContains();
    }

}
