package class09;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。
 */
public class Homework {

    public static void main(String[] args) {
//        method01();
        methodO2();
    }

    private static void method01() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(Homework.class);
        annotationConfigApplicationContext.refresh();
        User bean = annotationConfigApplicationContext.getBean(User.class);
        System.out.println(bean);
    }

    private static void methodO2() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/spring-context.xml");
        User student = beanFactory.getBean(User.class);
        System.out.println(student);
    }

    @Bean(name = "user")
    public User getUser() {
        User user = new User();
        user.setAddr("hangzhou");
        user.setName("zhengxiaojun");
        return user;
    }

}
