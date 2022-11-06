package ua.org.smit.galleryweb.controller;

import static java.lang.Thread.sleep;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.org.smit.authorization.AuthUser;
import ua.org.smit.authorization.RegistrationDto;
import ua.org.smit.authorization.exceptions.EmailNotUniqeException;
import ua.org.smit.authorization.exceptions.LoginAlreadExistException;
import ua.org.smit.authorization.exceptions.NickNameNotUniqeException;
import ua.org.smit.webcomponents.system.messages.SystemMessage;
import ua.org.smit.webcomponents.system.messages.SystemMessagesService;
import ua.org.smit.authorization.Authorization;
import ua.org.smit.authorization.Registration;
import static ua.org.smit.galleryweb.mvcconfig.WebAppConfig.frontEndData;

/**
 *
 * @author smit
 */
@Controller
public class AuthorizationController {

    @Autowired
    private Authorization authorization;

    @Autowired
    private Registration registration;

    @Autowired
    private SystemMessagesService systemMessagesService;

    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public Object signin(HttpSession session) {
        ModelAndView model = new ModelAndView("signin");
        Optional<AuthUser> user = authorization.find(session.getId());
        model.addObject("user", user);
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public String signinSubmit(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "password") String password,
            HttpSession session) {

        Optional<AuthUser> optional = authorization.findByLogin(login);

        if (!optional.isPresent()) {
            return "redirect:?err=login_not_found";
        } else {
            AuthUser user = optional.get();
            if (user.isPasswordEquals(password)) {
                user.signIn(session.getId());
                authorization.addActiveUser(user);
                return "redirect:/";
            } else {
                return "redirect:?err=wrong_password";
            }
        }
    }

    @RequestMapping(value = "/sign_out", method = RequestMethod.GET)
    public String signOut(HttpSession session) throws InterruptedException {
        authorization.signOut(session.getId());
        return "redirect:/";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public ModelAndView signUp(HttpServletRequest httpRequest) {
        ModelAndView model = new ModelAndView("signup");
        model.addObject("frontEndData", frontEndData);
        return model;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUpAction(
            String login,
            String nickName,
            String password,
            String email,
            HttpSession session) throws InterruptedException {

        sleep(1500);
        try {

            RegistrationDto regInfo = new RegistrationDto(login, nickName, password, email);
            registration.register(regInfo, session.getId());
            authorization.signInUser(login, password, session.getId());

            return "redirect:/home";
        } catch (LoginAlreadExistException ex) {
            systemMessagesService.add(SystemMessage.Type.WARNIND, "Login not uniqe!", session.getId());
        } catch (NickNameNotUniqeException ex) {
            systemMessagesService.add(SystemMessage.Type.WARNIND, "Nickname not uniqe!", session.getId());
        } catch (EmailNotUniqeException ex) {
            systemMessagesService.add(SystemMessage.Type.WARNIND, "Email not uniqe!", session.getId());
        } catch (IllegalArgumentException ex) {
            systemMessagesService.add(SystemMessage.Type.WARNIND, "login or email to long", session.getId());
        }

        return "redirect:?err=1";
    }

}
