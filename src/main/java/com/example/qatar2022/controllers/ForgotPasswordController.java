package com.example.qatar2022.controllers;

import com.example.qatar2022.config.Utility;
import com.example.qatar2022.entities.personne.User;
import com.example.qatar2022.service.UserService;
import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

  @Autowired private JavaMailSender mailSender;

  @Autowired private UserService userService;

  @GetMapping("/forgot_password")
  public String showForgotPasswordForm() {
    return "profile/forgot_password_form";
  }

  @PostMapping("/forgot_password")
  public String processForgotPassword(HttpServletRequest request, Model model) {
    String email = request.getParameter("email");
    // generating resetPassword token
    String token = RandomString.make(30);

    try {
      userService.updateResetPasswordToken(token, email);
      String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
      sendEmail(email, resetPasswordLink);
      model.addAttribute(
          "message",
          "Nous avons envoyé un lien de réinitialisation du mot de passe à votre adresse électronique. Veuillez vérifier.");
    } catch (UserPrincipalNotFoundException ex) {
      model.addAttribute("error", ex.getMessage());
    } catch (UnsupportedEncodingException | MessagingException e) {
      model.addAttribute("error", "Error while sending email");
    }
    return "profile/forgot_password_form";
  }

  public void sendEmail(String recipientEmail, String link)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("contact@shopme.com", "Support");
    helper.setTo(recipientEmail);

    String subject = "Voici le lien pour réinitialiser votre mot de passe";
    String content =
        "<p>Hello,</p>"
            + "<p>Vous avez demandé à réinitialiser votre mot de passe.</p>"
            + "<p>Cliquez sur le lien ci-dessous pour modifier votre mot de passe :</p>"
            + "<p><a href=\""
            + link
            + "\">Modifier mon mot de passe</a></p>"
            + "<br>"
            + "<p>Ignorez cet e-mail si vous vous souvenez de votre mot de passe, "
            + "ou vous n'avez pas fait la demande</p>"
            + "<br/><strong>Your UEFA TEAM</strong>";

    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(message);
  }

  @GetMapping("/reset_password")
  public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
    User user = userService.getByResetPasswordToken(token);
    model.addAttribute("token", token);

    if (user == null) {
      model.addAttribute("message", "Invalid Token");
      return "message";
    }
    return "profile/reset_password_form";
  }

  @PostMapping("/reset_password")
  public String processResetPassword(
      @RequestParam("token") String token,
      @RequestParam("password") String password,
      @Valid User user,
      BindingResult result,
      Model model) {

    model.addAttribute("title", "Reset your password");

    if (result.hasErrors()) {
      return "profile/reset_password_form";
    }

    userService.updatePassword(user, password);

    model.addAttribute("user", user);
    model.addAttribute("messageP", "You have successfully changed your password.");

    return "profile/message";
  }
}
