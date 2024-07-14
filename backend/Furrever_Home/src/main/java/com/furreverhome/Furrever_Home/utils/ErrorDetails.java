package com.furreverhome.Furrever_Home.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ErrorDetails {

    private Date date;

    private String Message;

    private String requestDescription;

    public ErrorDetails() {
    }
}
