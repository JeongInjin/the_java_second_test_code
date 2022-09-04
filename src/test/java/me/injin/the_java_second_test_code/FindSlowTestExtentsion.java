package me.injin.the_java_second_test_code;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

/**
 * Extentsion 기능 정의.
 * <p>좀더 자세히 알고 싶다면 아래를 참조 하자.</p>
 * @see "https://lannstark.tistory.com/217
 */
public class FindSlowTestExtentsion implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private final static Logger log = LogManager.getLogger(FindSlowTestExtentsion.class);
    private long THRESHOLD;

    public FindSlowTestExtentsion(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);
        String testMethodName = requiredTestMethod.getName();
        ExtensionContext.Store store = getStore(context);

        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if (duration > THRESHOLD && annotation == null) {
            log.info("================================================================================================");
            log.info("|                   Please consider mark method {} with @slowTest                   |", testMethodName);
            log.info("================================================================================================");
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
