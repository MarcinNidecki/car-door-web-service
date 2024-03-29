package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.ConfirmationToken;
import com.mnidecki.cardoor.domain.Mail;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.UserRole;
import com.mnidecki.cardoor.repository.ConfirmationTokenRepository;
import com.mnidecki.cardoor.repository.UserRepository;
import com.mnidecki.cardoor.repository.UserRoleRepository;
import com.mnidecki.cardoor.services.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private SimpleEmailService emailSenderService;

    public User save(final User user) {
        if (user.getRoles() == null || user.getRoles().size() <= 0) {
            UserRole userRole = userRoleRepository.getUserRoleByRoleName("ROLE_USER");
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        }

        return userRepository.save(user);
    }

    public User getUserByMail(final String email) {
        return userRepository.getUserByEmail(email).orElse(new User());
    }

    public User findUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserById(final Long id) {
        return userRepository.getUserById(id).orElse(null);
    }

    public boolean isUserExist(final String email) {
        User user = getUserByMail(email);
        return user.getEmail() != null;
    }

    public User getUserFromAuthentication() {
        User user = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userName = authentication.getName();
            user = findUserByEmail(userName);
        }
        return user;
    }

    public void sendConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        emailSenderService.send(new Mail(user.getEmail(), "Complete Registration!", "To confirm your account, please click here : "
                + "https://sleepy-oasis-69014.herokuapp.com/confirm-account?token=" + confirmationToken.getToken()));
    }
}
