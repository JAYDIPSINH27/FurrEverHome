package com.furreverhome.Furrever_Home.dto.user;

import com.furreverhome.Furrever_Home.validation.ValidEmail;

public record ResetEmailRequest(@ValidEmail String email) {
}
