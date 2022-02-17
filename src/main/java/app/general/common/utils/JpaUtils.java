package app.general.common.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;


public class JpaUtils {

    @SafeVarargs
    public static Expression<String> concat(CriteriaBuilder cb, String delimiter, Expression<String>... expressions) {
        Expression<String> result = null;
        for (int i = 0; i < expressions.length; i++) {
            boolean isFirstExpression = i == 0;
            boolean isLastExpression = i == (expressions.length - 1);
            Expression<String> expression = expressions[i];
            if (isFirstExpression && isLastExpression) {
                result = expression;
            } else if (isFirstExpression) {
                result = cb.concat(expression, delimiter);
            } else {
                result = cb.concat(result, expression);
                if (!isLastExpression) {
                    result = cb.concat(result, delimiter);
                }
            }
        }
        return result;
    }

}
