/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.bean;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullDependenciesValidator.class)
/**
 * Controlla che tutti gli attibuti passati in listFields sono not-null o, se allNull == true, anche tutti null.
 */
public @interface NotNullDependencies
{
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Lista di campi da validare, separati da ";".
     */
    String listField();
    
    /**
     * Boolean che specifica se sono ammessi anche tutti valori null.
     */
    boolean allNull();
    
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    /**
     * Per usare pi� volte la NotNullDependencies nello stesso Bean.
     */
    @interface List
    {
        NotNullDependencies[] value();
    }
}

