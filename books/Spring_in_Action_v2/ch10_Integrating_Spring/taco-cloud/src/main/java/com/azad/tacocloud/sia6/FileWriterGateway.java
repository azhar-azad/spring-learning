package com.azad.tacocloud.sia6;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/***
 * @MessagingGateway - Declares a message gateway. This annotation tells Spring Integration to generate an
 * implementation of this interface at run time - similar to how Spring Data automatically generates implementations
 * of repository interfaces. 
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
