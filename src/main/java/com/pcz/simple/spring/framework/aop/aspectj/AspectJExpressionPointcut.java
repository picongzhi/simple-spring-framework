package com.pcz.simple.spring.framework.aop.aspectj;

import com.pcz.simple.spring.framework.aop.ClassFilter;
import com.pcz.simple.spring.framework.aop.MethodMatcher;
import com.pcz.simple.spring.framework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * AspectJ 表达式切入点
 *
 * @author picongzhi
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    /**
     * 支持的切入点原语
     */
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        // execution
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    /**
     * 切入点表达式
     */
    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> cls) {
        return this.pointcutExpression.couldMatchJoinPointsInType(cls);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return this.pointcutExpression.matchesMethodExecution(method)
                .alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
