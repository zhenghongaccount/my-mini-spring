package org.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * 用于指定 Bean 的作用域类型。
 * <p>
 * 可将该注解标注在类或方法上，用于定义 Bean 在容器中的生命周期范围。
 * 默认作用域为单例（singleton）。
 * </p>
 *
 * <p>常见的作用域类型：</p>
 * <ul>
 *   <li><b>singleton</b>：单例模式，整个容器中只存在一个实例（默认）</li>
 *   <li><b>prototype</b>：原型模式，每次获取都会创建一个新的实例</li>
 *   <li><b>request</b>：每个 HTTP 请求创建一个实例（Web 环境）</li>
 *   <li><b>session</b>：每个 HTTP 会话创建一个实例（Web 环境）</li>
 * </ul>
 *
 * <p>示例：</p>
 * <pre>
 * &#64;Scope("prototype")
 * public class UserService {
 *     // ...
 * }
 * </pre>
 *
 * @author zhenghong
 * @date 2025/6/14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    /**
     * 指定作用域名称，默认是 singleton。
     *
     * @return 作用域名称
     */
    String value() default "singleton";

}
