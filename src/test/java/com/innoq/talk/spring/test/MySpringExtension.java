package com.innoq.talk.spring.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.mock.env.MockPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

public class MySpringExtension implements BeforeAllCallback, TestInstancePostProcessor, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        getTestContextManager(context).beforeTestClass();
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        getTestContextManager(context).prepareTestInstance(testInstance);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        getTestContextManager(context).afterTestClass();
        getStore(context).remove(context.getRequiredTestClass());
    }

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(MySpringExtension.class);

    private static TestContextManager getTestContextManager(ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        var store = getStore(context);
        return store.getOrComputeIfAbsent(testClass, TestContextManager::new, TestContextManager.class);
    }

    private static ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getRoot().getStore(NAMESPACE);
    }

    static class TestContextManager {

        private AnnotationConfigApplicationContext ctx;

        private TestContextManager(Class<?> testClass) {
            ctx = new TestContextBootstrapper(testClass).getApplicationContext();
        }

        void beforeTestClass() {
        }

        void afterTestClass() {
        }

        void prepareTestInstance(Object testInstance) {
            ctx.getAutowireCapableBeanFactory().autowireBeanProperties(testInstance, AUTOWIRE_NO, false);
        }
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyTestProperty {
        String key();
        String value();
    }

    static class TestContextBootstrapper {

        private AnnotationConfigApplicationContext ctx;

        TestContextBootstrapper(Class<?> testClass) {
            ctx = new AnnotationConfigApplicationContext();

            var testProperty = AnnotationUtils.findAnnotation(testClass, MyTestProperty.class);
            if (testProperty != null) {
                ctx.getEnvironment().getPropertySources().addFirst(new MockPropertySource().withProperty(testProperty.key(), testProperty.value()));
            }

            ctx.scan(Application.class.getPackageName());
            ctx.refresh();
        }

        AnnotationConfigApplicationContext getApplicationContext() {
            return ctx;
        }
    }
}
