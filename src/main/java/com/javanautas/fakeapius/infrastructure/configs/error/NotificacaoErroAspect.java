package com.javanautas.fakeapius.infrastructure.configs.error;

import com.javanautas.fakeapius.business.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class NotificacaoErroAspect {

    private final EmailService emailService;

    @Pointcut("@within(com.javanautas.fakeapius.infrastructure.configs.error.NotificacaoErro) || @annotation(com.javanautas.fakeapius.infrastructure.configs.error.NotificacaoErro)")
    public void notificacaoErroPointCut() {}

    @AfterThrowing(pointcut = "notificacaoErroPointCut()", throwing = "e")
    public void notificacaoErro(final Exception e){
        emailService.enviaEmailExcecao(e);
    }


}
