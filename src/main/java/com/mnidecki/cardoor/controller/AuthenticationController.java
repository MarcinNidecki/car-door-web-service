package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.ConfirmationToken;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.BookingSpecDto;
import com.mnidecki.cardoor.domain.dto.UserQuickFormDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login"); // resources/template/login.html
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView model = new ModelAndView();
        UserQuickFormDto user = new UserQuickFormDto();
        model.addObject("userDto", user);
        model.setViewName("register");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("userDto") @Valid UserQuickFormDto userDto, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        if (userService.isUserExist(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.setViewName("register");
        } else {
            System.out.println(userDto.getEmail());
            User user = userService.save(userMapper.mapToUser(userDto));
            userService.sendConfirmationToken(user);
            model.addObject("emailId", user.getEmail());
            model.addObject("msg", "User has been registered successfully");
            model.addObject("userDto", new UserQuickFormDto());
            model.setViewName("register"); // resources/template/register.html
        }
        return model;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addObject("firstname", user.getFirstname() + " " + user);
        model.setViewName("home"); // resources/template/home.html
        return model;
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ModelAndView model = new ModelAndView();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

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

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error"); // resources/template/home.html
        return model;
    }
}