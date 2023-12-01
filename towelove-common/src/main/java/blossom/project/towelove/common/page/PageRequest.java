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

package blossom.project.towelove.common.page;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页请求对象
 *
 * <p> {@link PageRequest}、{@link PageResponse}
 * 可以理解是防腐层的一种实现，不论底层 ORM 框架，对外分页参数属性不变
 *
 * @author jinbiao.zhang
 */
@Data
public class PageRequest {
    private static final Long  PAGE_NO = 1L;
    private static final Long PAGE_SIZE = 10L;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Long pageNo = PAGE_NO;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Long pageSize = PAGE_SIZE;

}
