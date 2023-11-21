package blossom.project.towelove.framework.redis.util;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.TreeMap;

/**
 * @author: 张锦标
 * @date: 2023/4/5 17:49
 * DoubleCacheUtil类
 * 用于为自定义二级缓存提供服务方法
 */
public class ElParser {
    public static String parse(String elString, TreeMap<String,Object> map){
        elString=String.format("#{%s}",elString);
        //创建表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        map.entrySet().forEach(entry->
                context.setVariable(entry.getKey(),entry.getValue())
        );

        //解析表达式
        Expression expression = parser.parseExpression
                (elString, new TemplateParserContext());
        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        String value = expression.getValue(context, String.class);
        return value;
    }

    public static void main(String[] args) {
        String elString="#mailAccount.from";
        String elString2="#user";
        String elString3="#p0";

        TreeMap<String,Object> map=new TreeMap<>();
        MailAccount mailAccount = new MailAccount();
        mailAccount.setFrom("hello");
        mailAccount.setPort(465);
        map.put("mailAccount",mailAccount);
        map.put("user","Hydra");

        String val = parse(elString, map);
        String val2 = parse(elString2, map);
        String val3 = parse(elString3, map);

        System.out.println(val);
        System.out.println(val2);
        System.out.println(val3);
    }
}
