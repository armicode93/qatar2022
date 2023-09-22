package com.example.qatar2022.config;

import javax.servlet.http.HttpServletRequest;

public class Utility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString(); // return the application url, so the user will be able to click the link in the email
        return siteURL.replace(request.getServletPath(), "");
    }
}

