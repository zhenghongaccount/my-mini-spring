package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;

/**
 * {@code InstantiationAwareBeanPostProcessor} 是 {@link org.springframework.beans.factory.config.BeanPostProcessor} 的一个扩展接口，
 * 提供了在 Spring 容器实例化 Bean（即调用构造器）之前的回调机制。
 * <p>
 * 实现此接口可以在 Bean 被实际创建之前进行拦截，用于实现例如动态代理、提前替代实例、
 * 或者其他定制化实例化逻辑。
 * <p>
 * 典型用例包括：
 * <ul>
 *     <li>创建 AOP 代理对象（如 AspectJ 自动代理）</li>
 *     <li>跳过默认构造流程并直接返回自定义 Bean 实例</li>
 *     <li>条件性地禁止某些 Bean 被实例化</li>
 * </ul>
 *
 * @author Spring
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *
 * @author zhenghong
 * @date 2025/6/12
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在目标 Bean 实例化（即调用构造器）之前执行。
     * <p>
     * 如果该方法返回一个非 {@code null} 的对象，那么 Spring 将跳过默认的实例化过程，
     * 直接使用返回的对象作为该 Bean 的实例。
     * <p>
     * 注意：此时 Bean 的依赖注入和初始化方法还未执行。
     *
     * @param beanClass 正在实例化的 Bean 的 Class 类型
     * @param beanName  Spring 容器中该 Bean 的名称
     * @return 要使用的 Bean 实例对象（通常是代理对象），或者 {@code null} 表示继续走默认实例化流程
     * @throws BeansException 如果创建过程出错，可以抛出该异常阻止 Bean 的进一步创建
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 在属性注入（依赖注入）之前对 {@link PropertyValues} 进行后置处理的扩展点。
     * <p>
     * 这个方法允许开发者自定义修改即将注入到 bean 实例中的属性值集合，
     * 例如实现对字段的自动注入（如 @Autowired）、值的校验、动态属性添加等。
     * <p>
     * 此方法在 Spring 容器进行属性填充（populate）阶段调用，执行时机早于
     * {@link BeanPostProcessor#postProcessBeforeInitialization}。
     *
     * @param pvs      容器准备注入到 bean 中的属性值集合，可以为 null。
     * @param bean     当前 bean 实例，已经完成实例化，但尚未进行属性注入。
     * @param beanName 当前 bean 在容器中的名称，便于识别或条件控制。
     * @return 处理后的属性值集合，不能为 null；通常返回原始 pvs，或其修改版本。
     * @throws BeansException 如果处理过程中出现错误，可抛出异常阻止创建流程。
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 实例化之后、属性填充（依赖注入）之前被调用的回调方法。
     * <p>
     * 返回 {@code true} 表示继续对该 Bean 进行属性注入；
     * 返回 {@code false} 则表示跳过后续的属性填充阶段，Spring 将不再自动注入该 Bean 的属性。
     * <p>
     * 这个方法适用于在属性注入前进行一些条件判断、缓存短路、或记录等操作。
     * <p>
     * 注意：此时 Bean 已经完成构造，但尚未进行依赖注入或初始化回调（如 {@code @PostConstruct}、InitializingBean 等）。
     *
     * @param bean     已实例化但尚未注入属性的 Bean 实例。
     * @param beanName 当前 Bean 在容器中的名称。
     * @return {@code true} 表示继续进行属性注入；{@code false} 表示跳过该 Bean 的属性注入。
     * @throws BeansException 如果在处理过程中发生异常，Spring 会中止该 Bean 的创建流程。
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 提供对 Bean 的“早期引用”能力，通常用于处理循环依赖场景下的代理暴露。
     * <p>
     * 当 Spring 检测到存在构造器外的循环依赖时（例如 A 注入 B，B 又注入 A），
     * 会在尚未完成属性注入和初始化前，调用此方法来尝试获取该 Bean 的“早期引用”。
     * <p>
     * 对于需要进行 AOP 增强的 Bean，开发者可在此方法中返回一个代理对象，
     * 以确保注入到其他 Bean 中的是代理对象而不是原始对象，避免 AOP 增强失效。
     * <p>
     * 默认实现直接返回原始 Bean，如果需要支持自动代理（如 AOP），
     * 可以在实现类中覆盖此方法并调用代理创建逻辑（如 wrapIfNecessary）。
     * <p>
     * 此方法的典型调用者是 {@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory}，
     * 在调用 {@code addSingletonFactory()} 注册 ObjectFactory 进行循环依赖处理时，
     * 使用该方法作为创建早期对象引用的入口。
     *
     * @param bean     原始实例化后的 Bean 对象（尚未属性填充与初始化）
     * @param beanName 当前 Bean 的名称，用于标识和条件控制
     * @return 提供给其他 Bean 引用的“早期引用”，可为原始对象或代理对象
     * @throws BeansException 如果创建早期引用时发生错误，可抛出该异常
     */
    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
