/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blossom.project.towelove.framework.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log 注解打印，可以标记在类或者方法上
 * 标记在类上，类下所有方法都会打印；标记在方法上，仅打印标记方法；
 * 如果类或者方法上都有标记，以方法上注解为准
 *
 * @author jinbiao.zhang
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoveLog {

    /**
     * 是否打印入参
     * @return true：打印 false：不打印
     */
    boolean req() default true;

    /**
     * 是否打印出参
     * @return true：打印 false：不打印
     */
    boolean resp() default true;
}
