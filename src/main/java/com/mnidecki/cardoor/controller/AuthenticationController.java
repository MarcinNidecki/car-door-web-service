package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.ConfirmationToken;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.BookingSpecDto;
import com.mnidecki.cardoor.domain.dto.UserRegisterQuickFormDto;
import com.mnidecki.cardoor.mapper.UserMapper;
import com.mnidecki.cardoor.repository.ConfirmationTokenRepository;
import com.mnidecki.cardoor.repository.UserRepository;
import com.mnidecki.cardoor.services.DBService.UserRoleService;
import com.mnidecki.cardoor.services.DBService.UserService;
import com.mnidecki.cardoor.services.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.mnidecki.cardoor.controller.ControllerConstant.REGISTER;


@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    SimpleEmailService emailSenderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = {"/login"})
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login"); // resources/template/login.html
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView model = new ModelAndView();
        UserRegisterQuickFormDto user = new UserRegisterQuickFormDto();
        model.addObject("userDto", user);
        model.setViewName(REGISTER);
        return model;
    }

    @PostMapping(value = "/register")
    public ModelAndView register(@ModelAttribute("userDto") @Valid UserRegisterQuickFormDto userDto, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        if (userService.isUserExist(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            model.setViewName(REGISTER);
        } else {
            User user = userService.save(userMapper.mapToUser(userDto));
            userService.sendConfirmationToken(user);
            model.addObject("emailId", user.getEmail());
            model.addObject("msg", "User has been registered successfully, open yor mail and click the link to " +
                    "activate yor account");
            model.addObject("userDto", new UserRegisterQuickFormDto());
            model.setViewName(REGISTER); // resources/template/register.html
        }
        return model;
    }

    @GetMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addObject("firstname", user.getFirstname() + " " + user);
        model.setViewName("home"); // resources/template/home.html
        return model;
    }

    @GetMapping(value = "/confirm-account")
    public ModelAndView confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ModelAndView model = new ModelAndView();
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setStatus(1);
            user.getRoles().add(userRoleService.getUserRoleByRoleName("ROLE_USER"));
            userRepository.save(user);
            model.setViewName("index");
            model.addObject("booking",new BookingSpecDto());
        } else {
            model.addObject("message", "The link is invalid or broken!");
            model.setViewName("error");
        }

        return model;
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        return modelAndView;
    }

    @GetMapping(value = "/error")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error"); // resources/template/home.html
        return model;
    }
}