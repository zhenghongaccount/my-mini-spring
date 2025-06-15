package org.springframework.util;

/**
 * 用于解析嵌入字符串中的占位符或表达式的策略接口。
 * <p>
 * 该接口通常被用于解析 Spring 配置中的占位符（如 "${...}"）或
 * Spring 表达式语言（SpEL，形如 "#{...}"）等动态值。
 * <p>
 * 常见的应用场景包括：
 * <ul>
 *     <li>解析 {@code @Value} 注解中的属性值</li>
 *     <li>解析 BeanDefinition 中的字符串值</li>
 *     <li>处理占位符替换和表达式求值</li>
 * </ul>
 *
 * @author zhenghong
 * @date 2025/6/14
 */
public interface StringValueResolver {
    /**
     * 解析给定的字符串值，可能包含占位符（如 "${...}"）或表达式（如 "#{...}"）。
     * 实现类应当返回解析之后的字符串，如果不需要解析可直接返回原值。
     *
     * @param strVal 原始字符串值
     * @return 解析后的字符串结果（可以是原值，也可以是替换/计算后的值）
     */
    String resolveStringValue(String strVal);

}
