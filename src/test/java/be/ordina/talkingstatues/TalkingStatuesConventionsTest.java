package be.ordina.talkingstatues;

import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
import java.util.Set;

public class TalkingStatuesConventionsTest {

    @Test
    public void verifyRestMaturity() {
        Assert.assertTrue(true);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("be.ordina.talkingstatues"))
                .setScanners(new MethodAnnotationsScanner()));
        // Set<Class<? extends SomeType>> subTypes = reflections.getSubTypesOf(RestController.class);
        // Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RestController.class);

        Set<Method> methods = reflections.getMethodsAnnotatedWith(PostMapping.class);
        if (methods.size() > 0) {
            for (Method m : methods) {
                System.out.println("Flagging [" + m.getDeclaringClass().getCanonicalName() + "#" + m.getName() + "] with incorrect use of @PostMapping.");
            }
        }
        Assert.assertEquals("Found " + methods.size() + " violations.", 0, methods.size());
    }
}
