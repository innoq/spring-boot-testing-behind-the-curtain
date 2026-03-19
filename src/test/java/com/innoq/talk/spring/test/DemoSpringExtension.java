package com.innoq.talk.spring.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.MapPropertySource;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

public class DemoSpringExtension implements BeforeAllCallback, TestInstancePostProcessor, AfterAllCallback {

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
        removeTestContextManager(context);
    }

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DemoSpringExtension.class);

    private static TestContextManager getTestContextManager(ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        var store = getStore(context);
        return store.computeIfAbsent(testClass, TestContextManager::new, TestContextManager.class);
    }

    private static void removeTestContextManager(ExtensionContext context) {
        Class<?> testClass = context.getRequiredTestClass();
        var store = getStore(context);
        store.remove(testClass);
    }

    private static ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getRoot().getStore(NAMESPACE);
    }

    static class TestContextManager {

        private final AnnotationConfigApplicationContext ctx;

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

    @Target(TYPE)
    @Retention(RUNTIME)
    public @interface TestProperty {
        String key();
        String value();
    }

    static class TestContextBootstrapper {

        private final AnnotationConfigApplicationContext ctx;

        TestContextBootstrapper(Class<?> testClass) {
            ctx = new AnnotationConfigApplicationContext();

            var testProperty = AnnotationUtils.findAnnotation(testClass, TestProperty.class);
            if (testProperty != null) {
                ctx.getEnvironment().getPropertySources()
                        .addFirst(new MapPropertySource("test", Map.of(testProperty.key(), testProperty.value())));
            }

            ctx.scan(Application.class.getPackageName());
            ctx.refresh();
        }

        AnnotationConfigApplicationContext getApplicationContext() {
            return ctx;
        }
    }
}
