package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.UserDto;
import com.exadel.sandbox.team2.dto.report.ReportOnEmployeesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnUsersDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends BaseDtoService<User, UserDto> {

    Set<Role> getRoles(User user);

    void assignUserRole(Long userId, Long roleId);

    void unassignUserRole(Long userId, Long roleId);

    Optional<User> findInvitedUserByAuthorizationCode(String code);

    Optional<User> findUserByChatIdOrPhoneNumber(String chatId, String phone);

    List<User> findAllByStatus(UserState state);

    Optional<User> findByUsername(String username);

    Optional<User> findActiveUserByChatId(String chatId);

    List<ReportOnUsersDto> getDataForReportOnUsers(Date userBookDateFrom, Date userBookDateTo);

    List<ReportOnEmployeesDto> getDataForEmployeesReport(Date userCreateDateFrom, Date userCreateDateTo);
}