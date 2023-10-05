/*package com.example.qatar2022.controllers.errors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;


@ControllerAdvice
public class GlobalExceptionHandler {

    // to get the error, to register log message
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Model model, Exception ex) {
        logger.error("Errore generic");
        model.addAttribute("errorMessage", "Errore generic: " );
        return "error/error";
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(Model model, BindException ex) {
        logger.warn("Validation exception", ex);
        model.addAttribute("errorMessage", "Validation exception: " + ex.getMessage());
        return "error/error";
    }


    //Cette méthode gère toutes les autres exceptions qui ne sont pas capturées par les autres méthodes.

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowable(Model model, Throwable ex) {
        logger.error("Exception non gérée", ex);
        model.addAttribute("errorMessage", "Erreur interne du serveur");
        return "error/error";
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(Model model, NoHandlerFoundException ex) {
        logger.error("URL pas valide", ex);
        model.addAttribute("errorMessage", "URL pas valide");
        return "error/error";
    }

    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateInputException(Model model, TemplateInputException ex) {
        logger.error("Erreur Thymeleaf", ex);
        model.addAttribute("errorMessage", "Erreur Thymeleaf");
        return "error/error";
    }
}

 */

