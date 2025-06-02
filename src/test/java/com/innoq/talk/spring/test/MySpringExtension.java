package com.innoq.talk.spring.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.env.MockPropertySource;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

public class MySpringExtension implements BeforeAllCallback, TestInstancePostProcessor, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(new MockPropertySource().withProperty("greeting", "Moin %s."));
        ctx.scan(Application.class.getPackageName());
        ctx.refresh();

        ctx.getAutowireCapableBeanFactory().autowireBeanProperties(testInstance, AUTOWIRE_NO, false);
    }

    @Override
    public void afterAll(ExtensionContext context) {
    }
}
