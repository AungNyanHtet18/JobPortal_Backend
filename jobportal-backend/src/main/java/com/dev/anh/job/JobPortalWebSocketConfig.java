package com.dev.anh.job;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.dev.anh.job.utils.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class JobPortalWebSocketConfig implements WebSocketMessageBrokerConfigurer{

	private static final String CHAT_USER_ATTRIBUTE = "chatUser";
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.addInterceptors(jwtHandshakeInterceptor())
			.setHandshakeHandler(jwtHandshakeHandler())
			.setAllowedOriginPatterns("*");
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
		registry.setUserDestinationPrefix("/user");
	}

	private HandshakeInterceptor jwtHandshakeInterceptor() {
		return new HandshakeInterceptor() {
			
			@Override
			public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Map<String, Object> attributes) {

				var token = UriComponentsBuilder.fromUri(request.getURI())
								.build()
								.getQueryParams()
								.getFirst("token");

				if(token != null) {
					try {
						attributes.put(CHAT_USER_ATTRIBUTE, jwtTokenProvider.parseAccessToken(token));
					} catch (Exception ignored) {
					}
				}

				return true;
			}

			@Override
			public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
					WebSocketHandler wsHandler, Exception exception) {
			}
		};
	}

	private DefaultHandshakeHandler jwtHandshakeHandler() {
		return new DefaultHandshakeHandler() {
			
			@Override
			protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
					Map<String, Object> attributes) {

				var user = attributes.get(CHAT_USER_ATTRIBUTE);

				if(user instanceof Principal principal) {
					return principal;
				}

				return super.determineUser(request, wsHandler, attributes);
			}
		};
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
			DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
			resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
			MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
			converter.setObjectMapper(new ObjectMapper());
			converter.setContentTypeResolver(resolver);
			messageConverters.add(converter);
		return false;
	}
}
