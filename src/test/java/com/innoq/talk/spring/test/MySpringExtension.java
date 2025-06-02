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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        private TestContext ctx;
        private final List<TestExecutionListener> listeners = List.of(new AutowiredTestExecutionListener(), new TransactionalTestExecutionListener());

        private TestContextManager(Class<?> testClass) {
            ctx = new TestContextBootstrapper(testClass).getTestContext();
        }

        void beforeTestClass() {
            listeners.forEach(listener -> listener.beforeTestClass(ctx));
        }

        void afterTestClass() {
            listeners.reversed().forEach(listener -> listener.afterTestClass(ctx));
        }

        void prepareTestInstance(Object testInstance) {
            ctx.setTestInstance(testInstance);
            listeners.forEach(listener -> listener.prepareTestInstance(ctx));
        }
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyTestProperty {
        String key();
        String value();
    }

    static class TestContextBootstrapper {

        private TestContext ctx;

        TestContextBootstrapper(Class<?> testClass) {
            var testProperty = AnnotationUtils.findAnnotation(testClass, MyTestProperty.class);
            ctx = new TestContext(testClass, testProperty);
        }

        TestContext getTestContext() {
            return ctx;
        }
    }

    static class TestContext {

        private static Map<Integer, AnnotationConfigApplicationContext> CACHE = new HashMap<>();

        private final Class<?> testClass;
        private final MyTestProperty testProperty;
        private Object testInstance;

        TestContext(Class<?> testClass, MyTestProperty testProperty) {
            this.testClass = testClass;
            this.testProperty = testProperty;
        }

        public Class<?> getTestClass() {
            return testClass;
        }

        public void setTestInstance(Object testInstance) {
            this.testInstance = testInstance;
        }

        public Object getTestInstance() {
            return testInstance;
        }

        public AnnotationConfigApplicationContext getApplicationContext() {
            return CACHE.computeIfAbsent(this.hashCode(), hash -> {
                var ctx = new AnnotationConfigApplicationContext();

                if (testProperty != null) {
                    ctx.getEnvironment().getPropertySources().addFirst(new MockPropertySource().withProperty(testProperty.key(), testProperty.value()));
                }

                ctx.scan(Application.class.getPackageName());
                ctx.refresh();

                return ctx;
            });
        }

        @Override
        public int hashCode() {
            if (testProperty == null) {
                return 0;
            }
            return Objects.hash(testProperty.key(), testProperty.value());
        }
    }

    public interface TestExecutionListener {
        default void beforeTestClass(TestContext ctx) {
        }

        default void afterTestClass(TestContext ctx) {
        }

        default void prepareTestInstance(TestContext ctx) {
        }
    }

    static class AutowiredTestExecutionListener implements TestExecutionListener {

        @Override
        public void prepareTestInstance(TestContext ctx) {
            ctx.getApplicationContext().getAutowireCapableBeanFactory().autowireBeanProperties(ctx.getTestInstance(), AUTOWIRE_NO, false);
        }
    }

    static class TransactionalTestExecutionListener implements TestExecutionListener {
        @Override
        public void beforeTestClass(TestContext ctx) {
            System.out.println("Starting transaction");
        }

        @Override
        public void afterTestClass(TestContext ctx) {
            System.out.println("Rolling back transaction");
        }
    }
}
