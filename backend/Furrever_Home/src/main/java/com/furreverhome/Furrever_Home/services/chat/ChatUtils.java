package com.furreverhome.Furrever_Home.services.chat;

import com.furreverhome.Furrever_Home.utils.jgravatar.Gravatar;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarDefaultImage;
import com.furreverhome.Furrever_Home.utils.jgravatar.GravatarRating;

public class ChatUtils {

    private final static int gravatarSize = 50;

    private ChatUtils() {
    }

    /**
     * Generates the URL for the Gravatar avatar associated with the given email address.
     *
     * @param email The email address used to generate the Gravatar URL.
     * @return The URL of the Gravatar avatar.
     */
    public static String getAvatarUrl(String email) {
        Gravatar gravatar = new Gravatar();
        gravatar.setSize(gravatarSize);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
        return gravatar.getUrl(email);
    }
}
