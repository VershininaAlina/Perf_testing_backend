package ru.alina.hr_backend_server.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.CloseStatus;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import ru.alina.hr_backend_server.controller.video.VideoService;

import ru.alina.hr_backend_server.entity.test.UserTestPass;
import ru.alina.hr_backend_server.entity.test.UserTestStatus;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.repository.UserRepository;
import ru.alina.hr_backend_server.repository.UserTestPassedRepository;
import ru.alina.hr_backend_server.service.JwtService;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserRepository userRepository;
    private final UserTestPassedRepository userTestPassedRepository;


    private final JwtService jwtService;
    private final VideoService videoService;

    // socket id, socket session
    private HashMap<String, WebSocketSession> connection = new HashMap<>();
    //user-id,socket-connection
    private HashMap<Long, WebSocketSession> channels = new HashMap<>();


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
        registry.enableSimpleBroker("/topic", "/topic2");
        //  registry.setCacheLimit(4096 * 8 * 1024);
        registry.setCacheLimit(1024 * 8 * 4096 * 100000);

    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean factoryBean = new ServletServerContainerFactoryBean();
        factoryBean.setMaxTextMessageBufferSize(2048 * 2048 * 2);
        factoryBean.setMaxBinaryMessageBufferSize(2048 * 2048 * 2);
        factoryBean.setMaxSessionIdleTimeout(2048L * 2048L * 2);
        factoryBean.setAsyncSendTimeout(2048L * 2048L * 2);
        return factoryBean;
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/task").setAllowedOriginPatterns("*").withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                new ChannelInterceptor() {
                    @Override
                    public Message<?> preSend(Message<?> message, MessageChannel channel) {


                        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                        WebSocketSession webSocketSession = connection.get(accessor.getSessionId());

                        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                            String token = accessor.getFirstNativeHeader("Authorization");

                            if (token != null && token.startsWith("Bearer ")) {
                                if (accessor.getSessionAttributes().get("user") == null) {

                                    String jwt = token.substring(7); // Extract the actual token without "Bearer "
                                    if (jwtService.validateToken(jwt)) {

                                        String email = jwtService.extractUsername(jwt); // Extract the email from the token
                                        User user = userRepository.getUserByEmail(email);

                                        if (user != null) {
                                            synchronized (user.getId()) {

                                                List<UserTestPass> userTestPasses = userTestPassedRepository.getUserTestPassByUserAndTimeEndIsNull(user);

                                                if (userTestPasses.size() == 0) {
                                                    closeConnection(webSocketSession, "You not started test");
                                                    return null;
                                                }

                                                if (channels.get(user.getId()) == null) {
                                                    if (webSocketSession != null) {
                                                        channels.put(user.getId(), webSocketSession);
                                                        accessor.getSessionAttributes().put("user", user);
                                                        accessor.setUser(new UsernamePasswordAuthenticationToken(user, null));
                                                    }
                                                } else {
                                                    closeConnection(webSocketSession, "You are already connected to this topic.");
                                                    return null;
                                                }
                                            }
                                        } else {
                                            closeConnection(webSocketSession, "You  not authorized");
                                            return null;
                                        }
                                    }
                                }
                            }
                        }


                        if (StompCommand.SEND.equals(accessor.getCommand())) {
                            if (webSocketSession.getAttributes().get("user") == null) {
                                closeConnection(webSocketSession, "You  not authorized");
                                return null;
                            }
                        }

                        return message;
                    }
                }
        );
    }


    private static boolean closeConnection(WebSocketSession webSocketSession, String message) {
        System.out.println("Close cuz: " + message);
        if (webSocketSession != null) {
            try {
                webSocketSession.close(CloseStatus.POLICY_VIOLATION.withReason(message));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }


    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {

                        connection.put(session.getId(), session);
                        super.afterConnectionEstablished(session);

                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        connection.remove(session.getId());


                        if (session.getAttributes().get("user") != null) {
                            var user = (User) session.getAttributes().get("user");
                            channels.remove(user.getId());
                            List<UserTestPass> userTestPasses = userTestPassedRepository.getUserTestPassByUserAndTimeEndIsNull(user);

                            for (UserTestPass userTestPass : userTestPasses) {
                                userTestPass.setTimeEnd(new Date());
                                userTestPass.setUserTestStatus(UserTestStatus.PASSED);
                                userTestPassedRepository.save(userTestPass);

                            }
                            videoService.stopVideoThread(user);

                        }

                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });

        registration.setMessageSizeLimit(1024 * 1024); // Set the desired message size limit in bytes
    }


}