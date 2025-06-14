package org.springframework.stereotype;

import java.lang.annotation.*;

/**
 * 用于标注一个类为组件（Bean），让容器能够自动扫描并注册为 Bean。
 * <p>
 * 通常与组件扫描机制（如 Spring 的类路径扫描）配合使用，将类纳入容器管理。
 * </p>
 *
 * <p>使用方式：</p>
 * <pre>
 * &#64;Component
 * public class UserService { ... }
 *
 * &#64;Component("customBeanName")
 * public class OrderService { ... }
 * </pre>
 *
 * @author zhenghong
 * @date 2025/6/1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

}
