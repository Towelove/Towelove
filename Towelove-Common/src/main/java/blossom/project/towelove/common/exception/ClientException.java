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

package blossom.project.towelove.common.exception;


import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.exception.errorcode.IErrorCode;

/**
 * 客户端异常
 *
 * @author zjb
 */
public class ClientException extends AbstractException {
    
    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }
    
    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }
    
    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }
    
    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }
    
    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
