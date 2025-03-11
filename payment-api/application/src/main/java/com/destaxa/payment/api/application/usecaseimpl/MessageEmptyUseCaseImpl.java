package com.destaxa.payment.api.application.usecaseimpl;

import com.destaxa.payment.api.usecase.MessageEmptyUseCase;
import org.jpos.iso.ISOMsg;

public class MessageEmptyUseCaseImpl implements MessageEmptyUseCase {

    @Override
    public Boolean isEmpty(ISOMsg msg) {
        return msg.getString(39) == null;
    }
}
