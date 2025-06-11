package org.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * {@code BeforeAdvice} 是一种标记接口，用于在方法执行之前执行通知逻辑。
 * <p>
 * 它是 AOP（面向切面编程）中“前置通知”的标志接口，通常与 AOP 框架（如 Spring AOP）一起使用。
 * 实现此接口的类可在目标方法调用之前执行某些操作（如日志记录、安全检查、事务管理等）。
 * </p>
 * <p>
 * 该接口本身不定义任何方法，它的行为由 AOP 框架识别并根据实现类进行处理。
 * </p>
 *
 * @see org.aopalliance.aop.Advice
 *
 * @author zhenghong
 * @date 2025/6/11
 */
public interface BeforeAdvice extends Advice {
}
