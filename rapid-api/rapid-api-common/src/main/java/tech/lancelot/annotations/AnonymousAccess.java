package tech.lancelot.annotations;

import java.lang.annotation.*;

/**
 * @author lancelot
 */
@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
