package com.destaxa.autorize.server.infraestructure.application.usecaseimpl;

import com.destaxa.autorize.server.infraestructure.usecase.CalculateAuthorizationCode;
import org.jpos.iso.ISOMsg;

import java.util.Random;

public class CalculateAuthorizationCodeImpl implements CalculateAuthorizationCode {

    private static final String DIGITIS = "0123456789";
    private static final int CODE_LENGTH = 6;

    @Override
    public String calculate(String responseCode) {
        if (responseCode != null && responseCode.equals("000")) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder(CODE_LENGTH);

            for (int i = 0; i < CODE_LENGTH; i++) {
                int randomIndex = random.nextInt(DIGITIS.length());
                char randomChar = DIGITIS.charAt(randomIndex);
                sb.append(randomChar);
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}
