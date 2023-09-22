/*package com.example.qatar2022.controllers.errors;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class )
    public String handleGenericException(Model model, Exception ex)
    {
        model.addAttribute("errorMessage", "erreur"+ ex.getMessage());
        return "error";
    }


}

 */
